package br.unb.cic.redes.Model

import java.net.ServerSocket

import akka.actor.ActorSystem
import br.unb.cic.redes.Model.models.User

/**
  * Created by @brenoxp on 11/10/16.
  */
object Main {

  val system = ActorSystem("ChatSystem")

  def main(args: Array[String]) {

    try {
      val server = new ServerSocket(9999)
      println("Server started on port " + server.getLocalPort)

      while (true) {
        val socket = server.accept()

        val user = new User(socket.hashCode().toString, socket)

//        Manager.addUser(user)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
