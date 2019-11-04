package org.example.profile

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import org.example.protocols.Profile
import play.api.libs.json.{Format, Json}

sealed trait ProfileCommand
object ProfileCommand


case object GetProfile extends ProfileCommand with ReplyType[Profile]

final case class CreateProfile(profile: Profile) extends ProfileCommand with ReplyType[Done]
object CreateProfile { implicit val format: Format[CreateProfile] = Json.format }

final case class UpdateProfile(newProfile: Profile) extends ProfileCommand with ReplyType[Done]
object UpdateProfile { implicit val format: Format[UpdateProfile] = Json.format }

case object DeleteProfile extends ProfileCommand with ReplyType[Done]
