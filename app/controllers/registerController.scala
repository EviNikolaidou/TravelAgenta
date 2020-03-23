package controllers


import helpers.constants
import javax.inject.{Inject, Singleton}
import models.loginDetails
import models.registerDetails
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}

@Singleton
class registerController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def register(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register(registerDetails.registerForm))
  }

  def registerSubmit(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    registerDetails.registerForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.register(formWithErrors))
    }, { signUpDetails =>
      if (registerDetails.PasswordValidation(signUpDetails) && registerDetails.UserValidation(signUpDetails)) {
        registerDetails.addUser(signUpDetails.username, signUpDetails.passwordcheck)
        Redirect(routes.HomeController.index()).withSession(request.session + ("username" -> signUpDetails.username))
      } else if (registerDetails.UserValidation(signUpDetails)) {
        BadRequest("Passwords don't match")
      } else {
        BadRequest("Account already exist")
      }
    })
  }

}