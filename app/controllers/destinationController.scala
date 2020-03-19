package controllers

import akka.stream.Materializer
import helpers.constants
import javax.inject.Inject
import models.{DestinationDetails, registerDetails}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.play.json._
import collection._
import play.api.libs.json.Json
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.api.Cursor
import reactivemongo.core.actors.AuthRequest

import scala.concurrent.{ExecutionContext, Future}
import helpers.JsonFormats._
import services.MongoServices
import ExecutionContext.Implicits.global

class destinationController @Inject()(components: ControllerComponents, val mongoServices: MongoServices) extends AbstractController(components) with play.api.i18n.I18nSupport{


//  def findByName(lastName: String): Action[AnyContent] = Action.async {
//    val cursor: Future[Cursor[registerDetails]] = collection.map {
//      _.find(Json.obj("lName" -> lastName)).
//        sort(Json.obj("created" -> -1)).
//        cursor[registerDetails]()
//    }
//
//    val futureUsersList: Future[List[registerDetails]] =
//      cursor.flatMap(
//        _.collect[List](
//          -1,
//          Cursor.FailOnError[List[registerDetails]]()
//        )
//      )
//
//    futureUsersList.map { persons =>
//      Ok(persons.toString)
//    }
//  }

//  def Destination: Action[AnyContent] = authRequest.async { implicit request =>
//    Future {
//      Ok(
//        views.html.dest(
//          DestinationDetails.destForm.fill(
//            DestinationDetails(
//              Some(BSONObjectID.generate().stringify),
//              constants.emptyString.toString,
//              constants.emptyString.toString,
//              request.session.get(constants.userName.toString).getOrElse(constants.emptyString.toString)
//            )
//          )
//        )
//      )
//    }
//  }

  def destSubmit: Action[AnyContent] = Action.async { implicit request =>
    DestinationDetails.destForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.dest(formWithErrors))
        }
      }, { destination =>
//        collection.flatMap(_.insert(destination))
//          .map(_ =>
//            Redirect(routes.HomeController.index())
//          )
        mongoServices.createDestinationDetails(destination).map( result =>
          Redirect(routes.HomeController.index())
        )

      }
    )
  }
}


