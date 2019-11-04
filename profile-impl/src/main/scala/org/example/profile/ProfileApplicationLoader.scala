package org.example.profile

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

abstract class ProfileApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents
    with CassandraPersistenceComponents {

  override lazy val lagomServer: LagomServer = serverFor[ProfileService](wire[ProfileServiceImpl])
  override lazy val jsonSerializerRegistry: JsonSerializerRegistry = ProfileSerializerRegistry
}

class ProfileApplicationLoader extends LagomApplicationLoader {
  override def loadDevMode(context: LagomApplicationContext) =
    new ProfileApplication(context) with LagomDevModeComponents

  override def load(context: LagomApplicationContext): ProfileApplication =
    new ProfileApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def describeService = Some(readDescriptor[ProfileService])
}