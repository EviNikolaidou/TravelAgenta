package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants


case class loginDetails(username:String, password: String)

object loginDetails {

  val loginForm - Form(
    mapping (
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText
    )
    (loginDetails.apply)(loginDetails.unapply)
  )

  def checkCredentials(login: loginDetails): Boolean = {
    if (login.username == constants.admin.toString && login.password == constants.password.toString)
      true
    else
      false
  }

  def checkUser(login: String): String = {
    if (login == constants.admin.toString)
      constants.admin.toString
    else
      constants.emptyString.toString
  }

}