
import java.io.PrintStream
import java.net.{InetAddress, Socket}

import scala.io.BufferedSource
import util.control.Breaks._

/**
  * Created by @brenoxp on 11/10/16.
  */
object ScalaApp {

  def main(args: Array[String]): Unit = {
    val socket = new Socket(InetAddress.getByName("localhost"), 9999)
    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val out = new PrintStream(socket.getOutputStream())

//    out.println("Hello, world")
//    out.flush()
//    println("Received: " + in.next())
//    s.close()

    breakable {
      for (ln <- io.Source.stdin.getLines) {

        out.println(ln)
        out.flush()

        if (ln == "stop") {
          println("Closing connection")
          socket.close()
          break
        }


        println("Received: " + in.next())
      }
    }
  }

}