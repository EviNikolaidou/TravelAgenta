
package services

import helpers.JsonFormats._
import helpers.constants
import javax.inject.Inject
import models.{loginDetails, registerDetails}
import play.api.libs.json.Json
import play.modules.reactivemongo.{ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

class MongoServices @Inject()(
                              val reactiveMongoApi: ReactiveMongoApi)
                               extends ReactiveMongoComponents {

  def getCollection(collectionName: String): Future[JSONCollection] = {
    database.map(_.collection[JSONCollection](collectionName))
  }

  def validUser(loginDetails: loginDetails) = {
    getCollection(constants.loginDetails.toString).map {
      _.find(
        Json.obj(
          constants.userName.toString -> loginDetails.username,
          constants.password.toString -> loginDetails.password
        )
      )
        .cursor[registerDetails]
    }.flatMap(_.collect[List]()).map(list => list.length == 1)
  }

//  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("user"))
//
//  //def destinationCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("destinations"))
//
//  def loginCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("destinations"))
//
//  def createUser(user: registerDetails): Future[WriteResult] = {
//    collection.flatMap(_.insert.one(loginDetails))
//  }

//  def createDestinationDetails(dest: DestinationDetails): Future[WriteResult] = {
//    destinationCollection.flatMap(_.insert.one(dest))
//  }

//  def createLoginDetails(loginDetails: registerDetails): Future[WriteResult] = {
//    destinationCollection.flatMap(_.insert.one(loginDetails))
//  }

//  def findAll(): Future[List[registerDetails]] = {
//    userCollection.map {
//      _.find(Json.obj())
//        .sort(Json.obj("created" -> -1))
//        .cursor[registerDetails]()
//    }.flatMap(
//      _.collect[List](
//        -1,
//        Cursor.FailOnError[List[registerDetails]]()
//      )
//    )
//  }

}