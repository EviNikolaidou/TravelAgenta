package models

import play.api.libs.json.OFormat

case class user (
            fname: String,
            lname: String,
            userName: String,
            destinations: List[Destination])

  case class Destination(
                   id: String,
                   country: String,
                   date: String,
                   notes: String)

  object JsonFormats {
    import play.api.libs.json.Json

    implicit val feedFormat: OFormat[Destination] = Json.format[Destination]
    implicit val userFormat: OFormat[user] = Json.format[user]
  }

