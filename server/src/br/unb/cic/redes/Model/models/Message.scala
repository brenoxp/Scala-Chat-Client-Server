package br.unb.cic.redes.Model.models

/**
  * Created by @brenoxp on 22/10/16.
  */
case class Message(message: String, user: User = null) {}

object Message {
  def prepareForClient(message: String) = {
    val lines = message.count(_ == '\n') + 1
    val res = lines + "\n" + message
    res
  }
}
