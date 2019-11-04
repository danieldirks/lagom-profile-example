package org.example.profile

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import org.example.protocols.Profile

import scala.concurrent.{ExecutionContext, Future}

class ProfileServiceImpl(registry: PersistentEntityRegistry)(implicit ec: ExecutionContext) extends ProfileService {
  registry.register(new ProfileEntity)

  override def getProfile(id: UUID): ServiceCall[NotUsed, Profile] = ServiceCall { _ =>
    println(s"Getting profile $id")
    val ref = registry.refFor[ProfileEntity](id.toString)
    ref.ask(GetProfile)
  }

  override def createProfile: ServiceCall[ProfileCreate, Profile] = ServiceCall { request =>
    val id = UUID.nameUUIDFromBytes(request.email.toLowerCase().getBytes())
    println(s"Creating profile $id")
    val createdProfile = new Profile(
      id = id,
      email = request.email,
      name = request.name
    )

    val ref = registry.refFor[ProfileEntity](id.toString)
    ref.ask(CreateProfile(createdProfile)).map(_ => createdProfile)
  }

  override def updateProfile(id: UUID): ServiceCall[Profile, Done] = ServiceCall { request =>
    println(s"Updating profile $id")
    val ref = registry.refFor[ProfileEntity](id.toString)
    ref.ask(UpdateProfile(request))
  }

  override def deleteProfile(id: UUID): ServiceCall[NotUsed, Done] = ServiceCall { _ =>
    println(s"Deleting profile $id")
    val ref = registry.refFor[ProfileEntity](id.toString)
    ref.ask(DeleteProfile)
  }

  override def ping: ServiceCall[NotUsed, Done] = ServiceCall { _ => Future(Done) }
}
