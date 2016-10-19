import java.io.{BufferedReader, InputStreamReader, PrintStream, PrintWriter}
import java.net.ServerSocket

import akka.actor.{ActorSystem, Props}

import util.control.Breaks._
import scala.io.BufferedSource

/**
  * Created by @brenoxp on 11/10/16.
  */
object ScalaApp {

  def main(args: Array[String]) {

    val system = ActorSystem("ChatSystem")
    // default Actor constructor
    val helloActor = system.actorOf(Props[Conection], name = "helloactor")
    helloActor ! "hello"
    helloActor ! "buenos dias"


    val MOTD = "Welcome to Scala Chat"

    try {

      val server = new ServerSocket(9999)

      while (true) {
        val socket = server.accept()
        println("accept soket")

        breakable {
          while (true) {
            println("init loop")

            val in = new BufferedSource(socket.getInputStream()).getLines()
            val out = new PrintStream(socket.getOutputStream())


            val res = in.next()
            println(res)

            out.println(res)
            out.flush()


            if (res == "stop") {
              println("Close connection")
              socket.close()
              break
            }
          }
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
