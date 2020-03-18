package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants

case class Destination(country: String, date: Date,reason:String)

object Destination {
  val destForm = Form(
    mapping(
      Constants.country.toString -> optional(nonEmptyText),
      Constants.date.toString -> nonEmptyText,
      Constants.notes.toString -> nonEmptyText,
    )(Destination.apply)(Destination.unapply)
    )
  )
}