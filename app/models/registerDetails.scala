package models

import play.api.data.Form
import play.api.data.Forms._
import helpers._
import play.api.libs.json.OFormat



case class registerDetails(fname:String, lname:String, username:String, password: String)

object registerDetails {

  val registerForm = Form[registerDetails](
    mapping(
      constants.fname.toString -> nonEmptyText,
      constants.lname.toString -> nonEmptyText,
      constants.userName.toString -> nonEmptyText,
      constants.password.toString -> nonEmptyText
    )(registerDetails.apply)(registerDetails.unapply)
  )
}

//object UserJsonFormats {
//import play.api.libs.json.Json
//import reactivemongo.play.json.collection.JSONCollection
//import play.api.libs.json._
//
//  implicit val userFormat: OFormat[registerDetails] =Json.format[registerDetails]
//}}