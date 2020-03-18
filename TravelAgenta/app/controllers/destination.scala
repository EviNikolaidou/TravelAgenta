package controllers

import akka.stream.Materializer
import authentication.AuthenticatedAction
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.AnyContent
import services.MongoServices
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.Future
import helpers.Constants
import javax.inject.Inject
import models.Recipe
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import helpers.JsonFormats._
import reactivemongo.play.json._

class Destination @Inject()
(val messagesApi: MessagesApi, val materializer: Materializer, val mongoServices: MongoServices) extends Controller
  with I18nSupport {

  def Destination: Action[AnyContent] = authRequest.async { implicit request =>
    Future{
      Ok(
        views.html.dest(
          Destination.destForm.fill(
            Destination(
              Some(BSONObjectID.generate().stringify),
              Constants.emptyString.toString,
              Constants.emptyString.toString,
              request.session.get(Constants.username.toString).getOrElse(Constants.emptyString.toString)
            )
          )
        )
      )
    }
  }

  def destSumbit: Action[AnyContent] = Action.async { implicit request =>
    Destination.destForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.dest(formWithErrors))
        }
      }, { destination =>
        mongoServices.getCollection(Constants.Destination.toString).flatMap(_.insert(destination))
          .map(_ =>
            Redirect(routes.application.index())
          )
      }
    )
  }

