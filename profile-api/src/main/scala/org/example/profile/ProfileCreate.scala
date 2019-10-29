package org.example.profile

import play.api.libs.json.{Format, Json}

case class ProfileCreate(email: String, name: String)

object ProfileCreate {
  implicit val format: Format[ProfileCreate] = Json.format[ProfileCreate]
}