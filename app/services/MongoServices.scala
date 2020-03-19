
package services

import helpers.jsonFormats._
import javax.inject.Inject
import models.registerDetails
import play.api.libs.json.Json
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.api.Cursor
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json._
import reactivemongo.play.json.collection.{JSONCollection, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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