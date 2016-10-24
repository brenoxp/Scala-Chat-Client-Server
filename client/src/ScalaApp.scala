
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


//    lazy val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    lazy val in = new BufferedSource(socket.getInputStream()).getLines()
    val streamOut = new PrintStream(socket.getOutputStream())

    breakable {
      for (ln <- io.Source.stdin.getLines) {

        if(ln.length != 0) {
          streamOut.println(ln)
          streamOut.flush()

          if (ln == "/stop") {
            println("Closing connection")
            socket.close()
            break
          }

          val size = in.next().toInt

          for (i <- 1 to size) {
            println(in.next())
          }
        }
      }
    }
  }
}