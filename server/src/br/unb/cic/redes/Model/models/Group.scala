package br.unb.cic.redes.Model.models

import java.util.Calendar

import sun.util.calendar.BaseCalendar.Date

import scala.collection.mutable

/**
  * Created by @brenoxp on 22/10/16.
  */
case class Group(admin: User, groupName: String) {

  private val users = mutable.MutableList[User]()
  addPerson(admin)

  def addPerson(user: User) = users += user

  def canBeRemoved = if (users.length == 1) true else false
}

case class GroupMessage(user: User, message: String, date: Date)
