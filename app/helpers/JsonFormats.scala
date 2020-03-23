package helpers


import models.{Destination, loginDetails, registerDetails}
import play.api.libs.json._

object JsonFormats {

  implicit val registerFormat :OFormat[registerDetails] = Json.format[registerDetails]
  implicit val loginFormat :OFormat[loginDetails]= Json.format[loginDetails]
  implicit val destFormat :OFormat[Destination]= Json.format[Destination]

}