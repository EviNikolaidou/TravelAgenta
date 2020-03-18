
package services

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{DestinationDetails, registerDetails}
import helpers.jsonFormats._
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global

class MongoServices @Inject()(
                              val reactiveMongoApi: ReactiveMongoApi
                            ) extends ReactiveMongoComponents {

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("persons"))

  def createUser(user: registerDetails): Future[WriteResult] = {
    collection.flatMap(_.insert.one(user))
  }

  def findAll(): Future[List[registerDetails]] = {
    collection.map {
      _.find(Json.obj())
        .sort(Json.obj("created" -> -1))
        .cursor[registerDetails]()
    }.flatMap(
      _.collect[List](
        -1,
        Cursor.FailOnError[List[registerDetails]]()
      )
    )
  }

}