package UavSim

import KafkaProduser.Produzer
import akka.actor.{ActorRef, ActorSystem, Props}
import Server.WebServer
import akka.routing.ActorRefRoutee

//Main method
private object Main extends App {

  //uav system
  val system : ActorSystem = ActorSystem("UavSystem")

  //with kafka produer
  val kafkaActor : ActorRef = system.actorOf(Props[Produzer], "Produzer")

  //start web server to get flight data from map builder server
  val server = WebServer.start(system, kafkaActor)

}
