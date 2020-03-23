package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{Destination, user}
import models.JsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}

class ApplicationController @Inject()(
                                                    components: ControllerComponents,
                                                    val reactiveMongoApi: ReactiveMongoApi
                                                  ) extends AbstractController(components)
  with MongoController with ReactiveMongoComponents {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] = database.map(_.collection[JSONCollection]("persons"))

  def create: Action[AnyContent] = Action.async {
    val User = user( "Evi", "Nikolaidou", "evie97", List(Destination("id", "country", "date", "notes")))
    val futureResult = collection.flatMap(_.insert.one(User))
    futureResult.map(_ => Ok("User inserted"))
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[user].map { user =>
      collection.flatMap(_.insert.one(user)).map { _ => Ok("User inserted")
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findByName(lname: String): Action[AnyContent] = Action.async {
    val cursor: Future[Cursor[user]] = collection.map {
      _.find(Json.obj("lname" -> lname)).
        sort(Json.obj("created" -> -1)).
        cursor[user]()
    }

    val futureUsersList: Future[List[user]] =
      cursor.flatMap(
        _.collect[List](
          -1,
          Cursor.FailOnError[List[user]]()
        )
      )

    futureUsersList.map { persons =>
      Ok(persons.toString)
    }
  }



}