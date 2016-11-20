package br.unb.cic.redes.Model.models

import java.util.Calendar

import sun.util.calendar.BaseCalendar.Date

import scala.collection.mutable

/**
  * Created by @brenoxp on 22/10/16.
  */
case class Group(admin: User, groupName: String) {

  private var users = mutable.MutableList[User]()
  addPerson(admin)

  def addPerson(user: User) = users += user
  def removePerson(user: User) = users = users.filterNot(mUser => mUser.nickname == user.nickname)

  def canBeRemoved = if (users.length == 0) true else false

  def getUsers: mutable.MutableList[User] = users
}

case class GroupMessage(user: User, message: String, date: Date)
