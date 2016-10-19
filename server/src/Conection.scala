import akka.actor.Actor

/**
  * Created by @brenoxp on 14/10/16.
  */
class Conection extends Actor {


  def receive = {
    case "hello" => println("hello back")
    case "buenos dias"       => println("buenos dias proce também")
    case _ => println("Uqueee")
  }

}
