package br.unb.cic.redes.Model.models

import scala.collection.mutable

/**
  * Created by @brenoxp on 22/10/16.
  */
class Group(admin: User, groupName: String) {

  private val users = mutable.MutableList[User]()
  addPerson(admin)

  def addPerson(user: User) = users += user
}
