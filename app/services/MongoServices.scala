
package services

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.play.json._
import collection._
import models.{DestinationDetails, registerDetails}
import play.api.libs.json.{JsValue, Json}
import reactivemongo.api.Cursor
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import helpers.JsonFormats._

class MongoServices @Inject()(
                              val reactiveMongoApi: ReactiveMongoApi
                            ) extends ReactiveMongoComponents {

  def personsCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("persons"))
  def destinationCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("destinations"))
  def loginCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("destinations"))

  def createUser(user: registerDetails): Future[WriteResult] = {
    personsCollection.flatMap(_.insert.one(user))
  }

  def createDestinationDetails(dest: DestinationDetails): Future[WriteResult] = {
    destinationCollection.flatMap(_.insert.one(dest))
  }

  def createLoginDetails(loginDetails: registerDetails): Future[WriteResult] = {
    destinationCollection.flatMap(_.insert.one(loginDetails))
  }

  def findAll(): Future[List[registerDetails]] = {
    personsCollection.map {
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