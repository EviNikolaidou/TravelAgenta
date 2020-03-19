package controllers

import java.awt.Desktop.Action

import akka.stream.Materializer
import authentication.authRequest
import helpers.constants
import javax.inject.Inject
import models.{DestinationDetails, registerDetails}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents}
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.play.json._
import collection._
import play.api.libs.json.Json
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.Cursor
import reactivemongo.core.actors.AuthRequest

import scala.concurrent.{ExecutionContext, Future}

class destinationController @Inject()(messagesApi: MessagesApi, val materialize: Materializer,
                                      components: ControllerComponents,authRequest:AuthRequest,val mongoServices: ReactiveMongoApi)
  extends AbstractController(components) with MongoController with I18nSupport {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("persons"))



  def findByName(lastName: String): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[registerDetails]] = collection.map {
      _.find(Json.obj("lName" -> lastName)).
        sort(Json.obj("created" -> -1)).
        cursor[registerDetails]()
    }

    val futureUsersList: Future[List[registerDetails]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[registerDetails]]()
        )
      )

    futureUsersList.map { persons =>
      Ok(persons.toString)
    }
  }

  def Destination: Action[AnyContent] = authRequest.async { implicit request =>
    Future {
      Ok(
        views.html.dest(
          DestinationDetails.destForm.fill(
            Destination(
              Some(BSONObjectID.generate().stringify),
              constants.emptyString.toString,
              constants.emptyString.toString,
              request.session.get(constants.userName.toString).getOrElse(constants.emptyString.toString)
            )
          )
        )
      )
    }
  }

  def destSubmit: Action[AnyContent] = Action.async { implicit request =>
    DestinationDetails.destForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.dest(formWithErrors))
        }
      }, { destination =>
        collection(constants.country.toString).flatMap(_.insert(destination))
          .map(_ =>
            Redirect(routes.HomeController.index())
          )
      }
    )
  }
}


