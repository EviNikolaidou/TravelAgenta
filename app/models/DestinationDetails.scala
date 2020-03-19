package models

import play.api.data.Form
import play.api.data.Forms._
import helpers.constants

//todo change date format. It shouldn't be string

case class DestinationDetails(id: String, country: String, date:String, reason:String)

object DestinationDetails {

  val destForm: Form[DestinationDetails]
  mapping(
      constants.id.toString -> optional(nonEmptyText),
      constants.country.toString -> nonEmptyText,
      constants.date.toString -> nonEmptyText,
      constants.notes.toString -> nonEmptyText,
    )(DestinationDetails.apply)(DestinationDetails.unapply)

}