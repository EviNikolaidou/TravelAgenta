package controllers

import java.awt.Desktop.Action

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import helpers.Constants

import scala.concurrent.Future

class destinationController @Inject()
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
            Redirect(routes.ApplicationController.index())
          )
      }
    )
  }

