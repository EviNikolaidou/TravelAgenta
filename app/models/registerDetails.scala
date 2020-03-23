package models

import play.api.data.Form
import play.api.data.Forms._
import helpers._
import play.api.libs.json.OFormat
import models.loginDetails

import scala.collection.mutable.ListBuffer


case class registerDetails(fname:String, lname:String, username:String, password: String, passwordcheck: String)

object registerDetails {

  val registerForm: Form[registerDetails] = Form (
    mapping(
      constants.fname.toString -> nonEmptyText,
      constants.lname.toString -> nonEmptyText,
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText,
      constants.passwordcheck.toString -> nonEmptyText
    )(registerDetails.apply)(registerDetails.unapply)
  )

  def PasswordValidation(userDetails: registerDetails): Boolean = userDetails.password == userDetails.passwordcheck


  def UserValidation(userDetails: registerDetails): Boolean = {
    var newUser = true
    for (i <- 0 until loginDetails.userList.length) {
      if (loginDetails.userList(i).username == userDetails.username) {
        newUser = false
      }
    }
    newUser
  }

  def addUser(username: String, password: String): ListBuffer[loginDetails] = loginDetails.userList += loginDetails(username, password)

}
//object UserJsonFormats {
//import play.api.libs.json.Json
//import reactivemongo.play.json.collection.JSONCollection
//import play.api.libs.json._
//
//  implicit val userFormat: OFormat[registerDetails] =Json.format[registerDetails]
//}}