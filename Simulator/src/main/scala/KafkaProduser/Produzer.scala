package KafkaProduser


import KafkaProduser.Produzer.Route
import akka.actor.{Actor, Props}
import akka.event.Logging.LoggerException
import org.apache.kafka.clients.producer.ProducerRecord
import akka.event.Logging
import spray.json.{DefaultJsonProtocol, JsValue}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object Produzer {

  //case class Produze
  case class Route(mavjs : String)

  def props = Props(classOf[Produzer])

}


class Produzer extends Actor with KafkaProduzer {//with SenzLogger

  import context.system

  var mavArray1 =""
  var i : Int = 0

  val Produze = "Produze"
  val cancellable =
    system.scheduler.schedule(WAITBEGIN milliseconds,
      WAITCONTINUE milliseconds,
      self,
      Produze)

  val log = Logging(system, this)

  override def receive: Receive = {

    case Route(mavjs) =>
      //println(mavjs)
      if (mavArray1=="")
        mavArray1 += mavjs
      else
        mavArray1 += (", "+ mavjs)
      i += 1

    case Produze =>
      // produce messages
      if(mavArray1!=""){
        //println(mavArray1)
        println("Array size "+i)
        val record = new ProducerRecord[String, String](KAFKATOPIC, "["+mavArray1+"]")
        producer.send(record)
      }

      i = 0
      mavArray1 = ""
    //log.info(s"produced message ${record}") //Todo : uncomment this
  }
}
