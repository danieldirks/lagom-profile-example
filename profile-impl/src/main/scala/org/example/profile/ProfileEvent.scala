package org.example.profile

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventShards, AggregateEventTag}
import org.example.protocols.Profile
import play.api.libs.json.{Format, Json}

sealed trait ProfileEvent extends AggregateEvent[ProfileEvent] {
  override def aggregateTag: AggregateEventShards[ProfileEvent] = ProfileEvent.Tag
}

object ProfileEvent {
  val NumShards = 20
  val Tag: AggregateEventShards[ProfileEvent] = AggregateEventTag.sharded[ProfileEvent](NumShards)
}


final case class ProfileCreated(profile: Profile) extends ProfileEvent
object ProfileCreated { implicit val format: Format[ProfileCreated] = Json.format }

final case class ProfileUpdated(profile: Profile) extends ProfileEvent
object ProfileUpdated { implicit val format: Format[ProfileUpdated] = Json.format }

case object ProfileDeleted extends ProfileEvent
