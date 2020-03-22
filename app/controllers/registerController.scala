package controllers


import helpers.constants
import javax.inject.Inject
import models.{loginDetails, registerDetails}
import play.api.mvc
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents}
import services.MongoServices

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class registerController @Inject()
(cc: ControllerComponents, val mongoServices: MongoServices) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def register: mvc.Action[AnyContent] = Action.async { implicit request =>
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
      },{ Register =>
        mongoServices.collection(loginDetails).map(result =>
            Redirect("/").withSession(request.session + (constants.userName.toString -> Register.username))
          )

//      { signUpDetails =>
//        mongoServices.createLoginDetails(signUpDetails).map(result =>
//          Redirect("/").withSession(request.session + (constants.userName.toString -> registerDetails.userName))
//        )

      }
    )

  }
}


