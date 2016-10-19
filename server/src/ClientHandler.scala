//import java.io.{BufferedReader, InputStreamReader, PrintWriter}
//import java.net.{Socket, SocketException}
//
//import akka.actor.Actor
//import akka.actor.Actor.Receive
//
///**
//  * Created by @brenoxp on 14/10/16.
//  */
//class ClientHandler(socket: Socket, clientId: Int) extends Actor{
//
//  def act {
//    try {
//      val out = new PrintWriter(socket.getOutputStream(), true)
//      val in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
//
//      print("Client connected from " + socket.getInetAddress() + ":" + socket.getPort)
//      println(" assigning id " + clientId)
//
//      var inputLine = in.readLine()
//      while (inputLine != null)
//      {
//        println(clientId + ") " + inputLine)
//
//        inputLine = in.readLine()
//      }
//
//      socket.close()
//
//      println("Client " + clientId + " quit")
//    }
//    catch {
//      case e: SocketException =>
//        System.err.println(e)
//
//      case e: Exception =>
//        System.err.println(e.printStackTrace())
//
//      case e =>
//        System.err.println("Unknown error " + e)
//    }
//  }
//
//  override def receive: Receive = {
//    case WhoToGreet(who) => greeting = s"hello, $who"
//    case Greet           => sender ! Greeting(greeting)
//  }
//}
