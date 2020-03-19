package controllers

import javax.inject._
import play.api.mvc._
import authentication.{AuthenticationAction, authRequest}
@Singleton
class HomeController @Inject()
  (cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def index: Action[AnyContent] = authAction {
    Ok(views.html.index("Your new application is ready."))
  }

}