package models

import play.api.data.Form
import play.api.data.Forms._
import helpers._

import scala.collection.mutable.ListBuffer


case class loginDetails(username:String, password: String)

object loginDetails {

  val loginForm: Form[loginDetails]= Form(
    mapping (
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText

    ) (loginDetails.apply)(loginDetails.unapply)
  )

  var userList: ListBuffer[loginDetails] = ListBuffer(loginDetails("admin", "password"))

  def checkCredentials(userDetails: loginDetails): Boolean = userList.contains(userDetails)

  def getUsername(username: String): Option[loginDetails] = userList.find(user => user.username == username)



  //  def checkUser(loginDetails: String): String = {
  //    if (loginDetails == constants.admin.toString)
  //      constants.admin.toString
  //    else
  //      constants.emptyString.toString
  //  }



}