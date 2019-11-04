package org.example.profile

import org.example.protocols.Profile

object ProfileState {
  val empty = ProfileState(null)
}

final case class ProfileState(profile: Profile) {
  def isPersistent: Boolean = if (profile != null) true else false
}
