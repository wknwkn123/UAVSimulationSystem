package UavSim


import KafkaProduser.Produzer.Route
import UavEngine.UAV
import UavSim.UavActor.{StartUAV, StopUAV}
import akka.actor.{Actor, ActorRef, PoisonPill}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

//object PoisonPills

object UavActor{
  case class StartUAV()
  case class StopUAV()
}

class UavActor(produzer : ActorRef, uav: UAV) extends Actor with UavConf {

  import context.system

  //calcellable object to call move timely
  val fly = "fly"
  val cancellable1 =  //ToDo wait time can be get from uav start time
    system.scheduler.schedule(WAITBEGIN milliseconds, //ToDo :- can change as a random wait to start each drone at different times
      WAITCONTINUE milliseconds,
      self,
      fly)


  self ! StartUAV()


  def receive = {

    case StartUAV() =>
      val uavName = self.path.name
      println("--- UAV " + self.path.name + " Started ---")

    case fly =>

      val mavlink = uav.fly(produzer)

      if(mavlink.equals("")){
        println("----- stoping -----")
        this.cancellable1.cancel()
        context.stop(self)
        self ! StopUAV()

      }
      else
        produzer ! Route(mavlink)
        //produzer ! Produze(KAFKATOPIC, mavlink)

    case StopUAV() => //ToDo : Not working
      println("Stopping UAV...")
      wait(1000)
      //self ! PoisonPills

    case _ =>
      println("ERROR2: Case is not defined")

  }


}

