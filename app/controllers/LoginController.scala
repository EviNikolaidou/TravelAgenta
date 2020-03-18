package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import helpers.constants
import models.loginDetails
import javax.inject.Inject

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def login(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login(loginDetails.loginForm))
  }

  def loginSubmit(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    loginDetails.loginForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.login(formWithErrors))
    }, { login =>
      if (loginDetails.checkCredentials(login))
        Redirect(routes.ApplicationController.index()).withSession(request.session + ("username" -> login.username))
      else
        BadRequest("Incorrect username or password")
    })
  }

}