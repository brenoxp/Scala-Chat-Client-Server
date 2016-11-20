package br.unb.cic.redes.Model.models

import java.util.Calendar

import sun.util.calendar.BaseCalendar.Date

import scala.collection.mutable

/**
  * Created by @brenoxp on 22/10/16.
  */
case class Group(admin: User, groupName: String) {

  private var users = mutable.MutableList[User]()
  private var blackList = mutable.MutableList[User]()
  addPerson(admin)

  def addPerson(user: User) = users += user
  def removePerson(user: User) = users = users.filterNot(mUser => mUser.nickname == user.nickname)

  def canBeRemoved = users.isEmpty

  def getUsers: mutable.MutableList[User] = users

  def userExist(nickname: String) = users.exists(u => u.nickname == nickname)
  def userIsBlocked(nickname: String) = blackList.exists(u => u.nickname == nickname)

  def userCanReceiveMessage(nickname: String): Boolean = {
    val userFound = users.find(u => u.nickname == nickname)

    if (userFound.nonEmpty) {
      return !userFound.get.away
    }
    false
  }

  def getUser(nickname: String) = users.find(u => u.nickname == nickname).get

  def banUser(user: User) = {
    removePerson(user)
    blackList += user
  }

  def sendMessage(text: String) = {
    users.foreach(user => user.sendMessage(Message(text)))
  }
}

case class GroupMessage(user: User, message: String, date: Date)
