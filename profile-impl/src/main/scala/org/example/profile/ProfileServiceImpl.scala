package org.example.profile

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import org.example.protocols.Profile

import scala.concurrent.{ExecutionContext, Future}

class ProfileServiceImpl(registry: PersistentEntityRegistry)(implicit ec: ExecutionContext) extends ProfileService {

  override def getProfile(id: UUID): ServiceCall[NotUsed, Profile] = ???

  override def createProfile: ServiceCall[ProfileCreate, Profile] = ???

  override def updateProfile(id: UUID): ServiceCall[Profile, Done] = ???

  override def deleteProfile(id: UUID): ServiceCall[NotUsed, Done] = ???

  override def ping: ServiceCall[NotUsed, Done] = ServiceCall { _ => Future(Done) }
}
