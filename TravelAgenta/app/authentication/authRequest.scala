package authentication

import com.google.inject.Inject
import models.LoginDetails
import play.api.mvc.{ActionBuilder, ActionTransformer, AnyContent, BodyParsers, Request, Result, Results, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}

class authRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request)

class authAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[AuthenticatedRequest, AnyContent] {

  override def invokeBlock[A](request: Request[A], block: authRequest[A] => Future[Result]): Future[Result] = {
    request.session.get("username")
      .flatMap(username => LoginDetails.getUsername(username))
      .map(user => block(new AuthenticatedRequest(user.username, request)))
      .getOrElse(Future.successful(Results.Redirect("/login")))
  }

}