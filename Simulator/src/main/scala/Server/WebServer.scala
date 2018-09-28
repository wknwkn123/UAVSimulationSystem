package Server

import java.util

import UavEngine.UAV.{FlightJson, WayPointJson}
import UavEngine.{Flight, UAV, Waypoint}
import UavSim.UavActor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import net.liftweb.json
import net.liftweb.json.parse
import spray.json._

import scala.util.parsing.json._
import scala.io.StdIn


object WebServer {

  def start(systemRef: ActorSystem, kafkaActor : ActorRef) {

    implicit val system = systemRef
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("uavs") {
        //        get {
        parameters('uavs) { (uavs) =>
          val uavId = startSimulation(uavs, system, kafkaActor)
          //          }

          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, uavId))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 3001)

    println(s"Server online at http://localhost:3001/\n")

  }

  def startSimulation(flightPlans: String, system: ActorSystem, kafkaActor : ActorRef): String = {

    import UavEngine.UAV.MyJsonProtocol._
    import net.liftweb.json.DefaultFormats
    implicit val formats = DefaultFormats

    //println(uavs)
    val fli: FlightJson = flightPlans.parseJson.convertTo(fligthFormat)
    //println(flight.waypoints)
    val json = parse(fli.waypoints)
    val elements = (json \\ "waypoint").children
    //var m;
    var waypoints: util.ArrayList[Waypoint] = new util.ArrayList[Waypoint]
    for (acct <- elements) {
      val m = acct.extract[String]
      val way: WayPointJson = m.parseJson.convertTo[WayPointJson]
      waypoints.add(new Waypoint(way.NodeID, way.isTransferable, way.x, way.y, way.z))
      //println(m.parseJson.convertTo[WayPointJson])
    }
    var flight: Flight = new Flight(fli.flightID, fli.requestID, fli.UAVID, fli.departTime, fli.arrivalTime)
    flight.setFlightPath(waypoints)
    var uav: UAV = new UAV(fli.UAVID, fli.flightID, 50, 200, 5);

    //set a Flight plan to a UAV
    uav.addFlightPlan(flight)
    uav.setUAVOrigin()

    system.actorOf(Props(classOf[UavActor],kafkaActor, uav), uav.id)

    return uav.id

  }

}