package controllers

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import controllers.ApplicationController

import scala.concurrent.Future

class logoutController @Inject()(val messagesApi: MessagesApi, val materializer: Materializer) extends Controller
  with I18nSupport {

  def logout: Action[AnyContent] = Action.async { implicit request =>
    Future{
      Redirect(routes.ApplicationController.index()).withSession()
    }
  }

}