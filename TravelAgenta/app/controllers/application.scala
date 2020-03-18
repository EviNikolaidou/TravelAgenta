package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class application @Inject()
  (cc: ControllerComponents, authAction: AuthenticationAction) extends AbstractController(cc) {

  def index: Action[AnyContent] = authAction {
    Ok(views.html.index("Your new application is ready."))
  }

}