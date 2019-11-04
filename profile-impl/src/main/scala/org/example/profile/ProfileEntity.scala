package org.example.profile

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import org.example.protocols.Profile

final class ProfileEntity extends PersistentEntity {
  override type Command = ProfileCommand
  override type Event = ProfileEvent
  override type State = ProfileState

  override def initialState: ProfileState = ProfileState.empty

  override def behavior: Behavior = {
    case state if !state.isPersistent => initial
    case state if state.isPersistent => persistent
  }

  private val initial: Actions = {
    Actions()
      .onReadOnlyCommand[GetProfile.type, Profile] {
        case (GetProfile, ctx, _) =>
          ctx.invalidCommand("Profile " + entityId + " doesn't exist")
    }
      .onCommand[CreateProfile, Done] {
        case (CreateProfile(profile), ctx, _) =>
          ctx.thenPersist(ProfileCreated(profile))(_ => ctx.reply(Done))
      }
      .onEvent {
        case (ProfileCreated(profile), _) =>
          ProfileState(profile)
      }
  }

  private val persistent: Actions = {
    Actions()
      .onReadOnlyCommand[GetProfile.type, Profile] {
        case (GetProfile, ctx, state) => ctx.reply(state.profile)
      }
      .onCommand[CreateProfile, Done] {
        case (CreateProfile(_), ctx, _) =>
          ctx.invalidCommand("Profile " + entityId + " already exists")
          ctx.done
      }
      .onCommand[UpdateProfile, Done] {
        case (UpdateProfile(newProfile), ctx, _) =>
          ctx.thenPersist(ProfileUpdated(newProfile))(_ => ctx.reply(Done))
      }
      .onEvent {
        case (ProfileUpdated(newProfile), state) =>
          ProfileState(
            profile = state.profile.copy(name = newProfile.name))
      }
      .onCommand[DeleteProfile.type, Done] {
        case (DeleteProfile, ctx, _) =>
          ctx.thenPersist(ProfileDeleted)(_ => ctx.reply(Done))
      }
      .onEvent {
        case (ProfileDeleted, _) =>
          ProfileState.empty
      }
  }
}

object ProfileSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    JsonSerializer[CreateProfile],
    JsonSerializer[ProfileCreated],
    JsonSerializer[UpdateProfile],
    JsonSerializer[ProfileUpdated]
  )
}
