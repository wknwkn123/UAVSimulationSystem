package UavEngine


import java.util
import java.time.Instant

import UavEngine.UAV.{Coordinate, NodePoint, UAVJSON}
import akka.actor.ActorRef
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol

object UAV{

  case class Coordinate(coor: Array[String])
  case class NodePoint(coordinate: Coordinate, ID: String)
  case class UAVJSON(id: String,
                     planID: String,
                     coordinate: Coordinate,
                     time: Double,
                     startPoint: NodePoint,
                     endPoint: NodePoint)
  case class FlightJson(
                         flightID: String,
                         requestID: String,
                         UAVID: String,
                         departTime: Int,
                         arrivalTime: Int,
                         waypoints: String
                       )
  case class WayPointJson(
                           x: Double,
                           y: Double,
                           isTransferable: Boolean,
                           NodeID: String,
                           z: Double,
                         )
//  case class Operation(
//                           var nextAvailableTime: Double = 0,
//                           var currentX: Double = 10,
//                           var currentY: Double = 10,
//                           var currentZ: Double = 10,
//                           var currentRouteSegment: RouteSegment = null,
//                           var currentWayPoint: Waypoint = null,
//                           var remainingBatteryLevel: Double = 0,
//                           var state: Int = 0
//  )


  object MyJsonProtocol extends DefaultJsonProtocol {

    implicit val coordinateFormat = jsonFormat1(Coordinate)
    implicit val nodePointFormat = jsonFormat2(NodePoint)
    implicit val uavJsonFormat = jsonFormat6(UAVJSON)

    implicit val wayPointFormat = jsonFormat5(WayPointJson)
    implicit val fligthFormat = jsonFormat6(FlightJson)

  }

}

class UAV(var id: String, var planId: String, val max_speed: Double, val max_altitude: Double, var errorMargin: Double) {

  //Get from Old Jaya Implementation by NTU srudents

  import UavEngine.UAV.MyJsonProtocol._
  import spray.json._

  val conf = ConfigFactory.load()
  val KAFKATOPIC: String = conf.getString("kafka.topic")

  private var operation = new UAVOperation
  private var flightPlans = new util.ArrayList[Flight]
  private var speed = 0.005
  private var stopWork = false
  private var done = false
  private var current_plan = 0
  private var flying = false

  private var origin: Waypoint = _
  private var destination: Waypoint = _
  private[UavEngine] var xDirection = .0
  private[UavEngine] var yDirection = .0
  private[UavEngine] var zDirection = .0
  private[UavEngine] var prevX = .0
  private[UavEngine] var prevY = .0
  private[UavEngine] var prevZ = .0


  def setUAVOrigin(): Unit = if (this.flightPlans.size > 0) {
    this.operation.setCurrentX(this.flightPlans.get(0).getFlightPath.get(0).getX)
    this.operation.setCurrentY(this.flightPlans.get(0).getFlightPath.get(0).getY)
    this.operation.setCurrentZ(this.flightPlans.get(0).getFlightPath.get(0).getZ)
  }

  def getJSONData(): JsValue = {
    val coordinate : Coordinate = Coordinate( Array(operation.getCurrentX.toString(), operation.getCurrentY.toString(), operation.getCurrentZ.toString()))
    val startPoint = NodePoint( Coordinate(Array(origin.getX.toString, origin.getY.toString, origin.getZ.toString)), origin.getNodeID)
    val endPoint = NodePoint(Coordinate(Array(destination.getX.toString, destination.getY.toString, destination.getZ.toString)),destination.getNodeID)
    return UAVJSON(this.id, this.planId, coordinate, Instant.now.getEpochSecond, startPoint,endPoint).toJson
  }

  def addFlightPlan(job: Flight): Unit = flightPlans.add(job)

  def fly(produzer: ActorRef): String = {

    if (flightPlans.size > 0) {
      val plan = flightPlans.get(0)

      if (!this.flying && this.current_plan < plan.getFlightPath.size - 1) {

        println(plan.getFlightID + " Flying from waypoint : " + current_plan)
        //init flying between two waypoints
        origin = plan.getFlightPath.get(current_plan)
        destination = plan.getFlightPath.get(current_plan + 1)
        xDirection = destination.getX - origin.getX
        yDirection = destination.getY - origin.getY
        zDirection = destination.getZ - origin.getZ
        prevX = origin.getX
        prevY = origin.getY
        prevZ = origin.getZ
        current_plan += 1
        flying = true
      }
      if (flying) {
        //System.out.println("Flying......" + planId);
        if (Math.abs(operation.getCurrentX - destination.getX) > this.errorMargin || Math.abs(operation.getCurrentY - destination.getY) > this.errorMargin || Math.abs(operation.getCurrentZ - destination.getZ) > this.errorMargin) {

          //continue flying
          if (Math.abs(operation.getCurrentX - destination.getX) > this.errorMargin) operation.setCurrentX(operation.getCurrentX + this.speed * xDirection)
          if (Math.abs(operation.getCurrentY - destination.getY) > this.errorMargin) operation.setCurrentY(operation.getCurrentY + this.speed * yDirection)
          if (Math.abs(operation.getCurrentZ - destination.getZ) > this.errorMargin) operation.setCurrentZ(operation.getCurrentZ + this.speed * zDirection)

          if (Math.abs(operation.getCurrentX - prevX) > this.errorMargin || Math.abs(operation.getCurrentY - prevY) > this.errorMargin || Math.abs(operation.getCurrentZ - prevZ) > this.errorMargin) {

            //set current location as previous location for next point
            prevX = operation.getCurrentX
            prevY = operation.getCurrentY
            prevZ = operation.getCurrentZ
          }

          return getJSONData().toString
        }
        else {
          println("Error margin exeeded")
        }
        flying = false
        return ""

      } //end of if flying

      println("Flight plan " + plan.getFlightID + " completed.")
      println()
      flightPlans.remove(0)
      return ""

    }

    else {
      System.out.println("Flight Plan is empty")
      return ""
    }

  }

}

