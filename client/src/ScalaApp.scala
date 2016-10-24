
import java.io.{BufferedReader, InputStreamReader, PrintStream}
import java.net.{InetAddress, Socket}

import scala.io.BufferedSource
import util.control.Breaks._

/**
  * Created by @brenoxp on 11/10/16.
  */
object ScalaApp {

  def main(args: Array[String]): Unit = {
    val socket = new Socket(InetAddress.getByName("localhost"), 9999)
    println("connected to " + socket.getInetAddress.getHostAddress + "/" + socket.getPort + "...")

    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val streamOut = new PrintStream(socket.getOutputStream())

    printMessage(in)

    breakable {
      for (ln <- io.Source.stdin.getLines) {

        if(ln.length != 0) {
          streamOut.println(ln)
          streamOut.flush()

          if (ln == "/leave") {
            println("Closing connection...")
            socket.close()
            break
          }

          printMessage(in)
        }
      }
    }


  }

  def printMessage(in: Iterator[String]) = {
    val size = in.next().toInt

    if (size == 1) {
      val message = in.next()
      if (message != "ok") println(message)
    } else {
      for (i <- 1 to size) {
        println(in.next())
      }
    }

    println()
  }

}