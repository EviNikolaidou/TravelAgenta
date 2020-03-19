package controllers

import java.awt.Desktop.Action
import java.lang.ModuleLayer.Controller

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AbstractController,AnyContent, ControllerComponents}
import services.MongoServices
import helpers.constants
import models.registerDetails
import scala.concurrent.Future
import models.registerDetails
import services.MongoServices

@Singleton
class registerController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

//(val messagesApi: MessagesApi, val materialize: Materializer, val mongoServices: MongoServices)
//  extends Controller with I18nSupport {

  def register: Action[AnyContent] = Action.async { implicit request =>
    Future{Ok(views.html.register(registerDetails.registerForm))}
  }

  def registerSubmit = Action.async { implicit request =>
    registerDetails.registerForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.register(formWithErrors))
        }
      }, { signUpDetails =>
        MongoServices.collection(constants.register.toString).flatMap(_.insert(register))
          .map(_ =>
            Redirect("/").withSession(request.session + (constants.userName.toString -> register.username))
          )
      }
  }
}


