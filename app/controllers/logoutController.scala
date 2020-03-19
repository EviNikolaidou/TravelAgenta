package controllers

import java.awt.Desktop.Action
import java.lang.ModuleLayer.Controller

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents}

import scala.concurrent.Future

class logoutController @Inject()(
                                  components: ControllerComponents,
                                  val materialize: Materializer) extends AbstractController(components)
  with I18nSupport {

  //  def logout: Action[AnyContent] = Action.async { implicit request =>
  //    Future{
  //      Redirect(routes.HomeController.index()).withSession()
  //    }
  //  }
}