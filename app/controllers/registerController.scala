package controllers

import akka.stream.Materializer
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}

import scala.concurrent.Future


class registerController @Inject()
(val messagesApi: MessagesApi, val materializer: Materializer, val mongoServices: MongoServices)
  extends Controller with I18nSupport {

  def register: Action[AnyContent] = Action.async { implicit request =>
    Future{Ok(views.html.register(register.registerForm))}
  }

  def registerSubmit = Action.async { implicit request =>
    register.registerForm.bindFromRequest.fold(
      { formWithErrors =>
        Future {
          BadRequest(views.html.register(formWithErrors))
        }
      }, { signUpDetails =>
        mongoServices.getCollection(Constants.register.toString).flatMap(_.insert(register))
          .map(_ =>
            Redirect("/").withSession(request.session + (Constants.username.toString -> register.username))
          )
      }
    )
  }

