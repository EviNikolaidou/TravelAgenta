package
controllers

import java.awt.Desktop.Action
import java.lang.ModuleLayer.Controller

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import helpers.constants
import models.DestinationDetails

import scala.concurrent.Future

class destinationController @Inject()
(val messagesApi: MessagesApi, val materializer: Materializer, val mongoServices: MongoServices) extends Controller
  with I18nSupport {

  def Destination: Action[AnyContent] = authRequest.async { implicit request =>
    Future{
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

  def destSumbit: Action[AnyContent] = Action.async { implicit request =>
    DestinationDetails.destForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.dest(formWithErrors))
        }
      }, { destination =>
        mongoServices.getCollection(constants.country.toString).flatMap(_.insert(destination))
          .map(_ =>
            Redirect(routes.ApplicationController.index())
          )
      }
    )
  }

