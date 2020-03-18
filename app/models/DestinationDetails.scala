package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants


case class DestinationDetails(country: String, date:String, reason:String)

object DestinationDetails {
  val destForm = Form(
    mapping(
      constants.id.toString -> optional(nonEmptyText),
      constants.country.toString -> optional(nonEmptyText),
      constants.date.toString -> nonEmptyText,
      constants.notes.toString -> nonEmptyText,
    )(DestinationDetails.apply)(DestinationDetails.unapply)

  )
}