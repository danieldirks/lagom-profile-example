package org.example.protocols

import java.util.UUID

import play.api.libs.json.{Format, Json}

case class Profile(
  id: UUID,
  email: String,
  name: String
)

object Profile {
  implicit val format: Format[Profile] = Json.format[Profile]
}
