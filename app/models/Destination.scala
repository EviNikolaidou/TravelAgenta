package models

//import play.api.data.Form
//import play.api.data.Forms._
//import helpers.constants
//
////todo change date format. It shouldn't be string
//
//case class Destination(id: String, country: String, date:String, reason:String)
//
//object Destination {
//
//  val destForm: Form[Destination] = Form(
//    mapping(
//        constants.id.toString -> nonEmptyText,
//        constants.country.toString -> nonEmptyText,
//        constants.date.toString -> nonEmptyText,
//        constants.notes.toString -> nonEmptyText,
//      )(Destination.apply)(Destination.unapply)
//  )
//}