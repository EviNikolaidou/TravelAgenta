package controllers

import java.awt.Desktop.Action
import java.lang.ModuleLayer.Controller

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AnyContent}

import scala.concurrent.Future

class logoutController @Inject()(val messagesApi: MessagesApi, val materialize: Materializer) extends Controller
  with I18nSupport {

  def logout: Action[AnyContent] = Action.async { implicit request =>
    Future{
      Redirect(routes.HomeController.index()).withSession()
    }
  }
}