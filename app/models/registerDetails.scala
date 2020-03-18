package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants


case class registerDetails(fname:String, lname:String, username:String, password: String)

object registerDetails {
  val registerForm = Form(
    mapping(
      constants.fname.toString -> nonEmptyText,
      constants.lname.toString -> nonEmptyText,
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText
    )(registerDetails.apply)(registerDetails.unapply)
  )
}