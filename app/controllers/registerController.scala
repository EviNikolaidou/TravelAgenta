package controllers

import java.awt.Desktop.Action

import helpers.constants
import javax.inject.Inject
import models.registerDetails
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents}
import scala.concurrent.Future

@Singleton
class registerController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  //(val messagesApi: MessagesApi, val materialize: Materializer, val mongoServices: MongoServices)
  //  extends Controller with I18nSupport {

  def register: Action[AnyContent] = Action.async { implicit request =>
    Future {
      Ok(views.html.register(registerDetails.registerForm))
    }
  }

  def registerSubmit = Action.async { implicit request =>
    registerDetails.registerForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.register(formWithErrors))
        }
      },
      { signUpDetails =>
        MongoServices.collection(constants.register.toString).flatMap(_.insert(register))
          .map(_ =>
            Redirect("/").withSession(request.session + (constants.userName.toString -> registerDetails.userName))
          );

      }
    )

  }
}


