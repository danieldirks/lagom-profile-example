package org.example.profile

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.Service._
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import org.example.protocols.Profile

trait ProfileService extends Service {
  def getProfile(id: UUID): ServiceCall[NotUsed, Profile]            // curl /v1/profile/<uuid>
  def createProfile: ServiceCall[ProfileCreate, Profile]             // curl -X PUT -d '{"name":"<name>","email":"<email>"}' /v1/profile
  def updateProfile(id: UUID): ServiceCall[Profile, Done]            // curl -X POST -d '{"id":"<uuid>","email":"<email>","name":"<name>"}' /v1/profile/<uuid>
  def deleteProfile(id: UUID): ServiceCall[NotUsed, Done]            // curl -X DELETE /v1/profile/<uuid>

  def ping: ServiceCall[NotUsed, Done]

  override def descriptor: Descriptor = {
    named("profile").withCalls(
      restCall(Method.GET, "/v1/profile/:uuid", getProfile _),
      restCall(Method.PUT, "/v1/profile", createProfile),
      restCall(Method.POST, "/v1/profile/:uuid", updateProfile _),
      restCall(Method.DELETE, "/v1/profile/:uuid", deleteProfile _),

      restCall(Method.GET, "/ping", ping),
    ).withAutoAcl(true)
  }
}