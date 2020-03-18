package models

import play.api.data.Form
import play.api.data.Forms._

case class DestinationDetails(country: String, date: Date, reason:String)

object DestinationDetails {
  val destForm = Form(
    mapping(
      Constants.country.toString -> optional(nonEmptyText),
      Constants.date.toString -> nonEmptyText,
      Constants.notes.toString -> nonEmptyText,
    )(DestinationDetails.apply)(DestinationDetails.unapply)
    )
  )
}