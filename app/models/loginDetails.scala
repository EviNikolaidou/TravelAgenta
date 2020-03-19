package models

import play.api.data.Form
import play.api.data.Forms._
import helpers._


case class loginDetails(username:String, password: String)

object loginDetails {

  val loginForm: Form[loginDetails]= Form(
    mapping (
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText

    ) (loginDetails.apply)(loginDetails.unapply)
  )


  def checkCredentials(loginDetails: loginDetails): Boolean = {
    if (loginDetails.username == constants.admin.toString && loginDetails.password == constants.password.toString)
      true
    else
      false
  }

//  def checkUser(loginDetails: String): String = {
//    if (loginDetails == constants.admin.toString)
//      constants.admin.toString
//    else
//      constants.emptyString.toString
//  }
  val userList: List[loginDetails]= List(loginDetails("admin", "password"))

  def getUsername(username: String):Option[loginDetails] = userList.find(user => user.username == username)


}