package controllers

import helpers.constants
import javax.inject.{Inject, Singleton}
import models.LoginDetails
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}

@Singleton
class login @Inject()(cc: ControllerComponents) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def login(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login(login.loginForm))
  }

  def loginSubmit(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    login.loginForm.bindFromRequest.fold({ formWithErrors =>
      BadRequest(views.html.login(formWithErrors))
    }, { login =>
      if (login.checkIfUserIsValid(login))
        Redirect(routes.application.index()).withSession(request.session + ("username" -> login.username))
      else
        BadRequest("Incorrect username or password")
    })
  }

}