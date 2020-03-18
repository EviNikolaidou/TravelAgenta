package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants


case class login(username:String, password: String)

object login {

  val loginForm - Form(
    mapping (
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText
    )
    (login.apply)(login.unapply)
  )

  def checkCredentials(login: login): Boolean = {
    if (login.username == Constants.admin.toString && login.password == Constants.password.toString)
      true
    else
      false
  }

  def checkUser(login: String): String = {
    if (login == Constants.admin.toString)
      Constants.admin.toString
    else
      Constants.emptyString.toString
  }

}