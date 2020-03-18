package helpers


import models.{DestinationDetails, loginDetails, registerDetails}
import play.api.libs.json._

object jsonFormats {

  implicit val registerFormat :OFormat[registerDetails] = Json.format[registerDetails]
  implicit val loginFormat :OFormat[loginDetails]= Json.format[loginDetails]
  implicit val destFormat :OFormat[DestinationDetails]= Json.format[DestinationDetails]

}