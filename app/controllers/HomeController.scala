package controllers

import javax.inject._
import play.api.mvc._
import authentication.authRequest

@Singleton
class HomeController @Inject()
  (cc: ControllerComponents, authAction: authRequest) extends AbstractController(cc) {

  def index: Action[AnyContent] = authAction {
    Ok(views.html.index("Your new application is ready."))
  }

}