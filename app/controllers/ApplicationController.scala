package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.registerDetails
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import helpers.JsonFormats.registerFormat
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import services.MongoServices
import views.html.register


class ApplicationController @Inject()(
                                      components: ControllerComponents,
                                      val mongoServices: MongoServices
                                    )
  extends AbstractController(components) {

  implicit def ec: ExecutionContext = components.executionContext


  def create: Action[AnyContent] = Action.async {
    val user = registerDetails("John", "Smith", "jsmith", "password")
    mongoServices.createUser(user).map(_ => Ok("User inserted"))
  }

  def createFromJson: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[registerDetails].map { user =>
      mongoServices.createUser(user).map(_ => Ok("User inserted"))
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

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



}
