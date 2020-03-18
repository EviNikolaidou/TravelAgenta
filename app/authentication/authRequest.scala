package authentication

import com.google.inject.Inject
import play.api.mvc._
import helpers.constants

import scala.concurrent.{ExecutionContext, Future}

class authRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

class authAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[authRequest, AnyContent] {

  override def invokeBlock[A](request: Request[A], block: authRequest[A] => Future[Result]): Future[Result] = {
    request.session.get("username")
      .flatMap(username => loginDetails.getUsername(username))
      .map(user => block(new authRequest(user.username, request)))
      .getOrElse(Future.successful(Results.Redirect("/login")))
  }

}