package br.unb.cic.redes.Model

import br.unb.cic.redes.Model.models.User
import br.unb.cic.redes.Model.models.Message

import scala.collection.mutable

/**
  * Created by @brenoxp on 19/10/16.
  */
object Manager {

  private val MOTD = "Welcome to Scala Chat \nA nice place to meet people!"

  private val connections = mutable.MutableList[User]()


  /* Methods */

  def addUser(user: User) = connections += user
  def removeUser(user: User) = user

  def receiveMessage(message: Message): Unit = {
    val m = message.message.split(" ")

    if (m.isEmpty) return ()

    m(0) match {
      case "/help" => message.user.sendMessage(Message(MOTD))
      case _ => {
        message.user.sendMessage(Message("Unrecognized command: \"" + message.message + "\""))
      }
    }

  }

  def createGroup = {

  }

}
