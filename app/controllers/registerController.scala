package controllers//        collection.flatMap(_.insert(destination))
//          .map(_ =>
//            Redirect(routes.HomeController.index())
//          )

import java.awt.Desktop.Action

import helpers.constants
import javax.inject.Inject
import models.registerDetails
import play.api.mvc
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents}
import services.MongoServices

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class registerController @Inject()
(cc: ControllerComponents, val mongoServices: MongoServices) extends AbstractController(cc) with play.api.i18n.I18nSupport {

  //(val messagesApi: MessagesApi, val materialize: Materializer, val mongoServices: MongoServices)
  //  extends Controller with I18nSupport {

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
      },
      { signUpDetails =>
        mongoServices.createLoginDetails(signUpDetails).map(result =>
          Redirect("/").withSession(request.session + (constants.userName.toString -> signUpDetails.username))
        )

      }
    )

  }
}


