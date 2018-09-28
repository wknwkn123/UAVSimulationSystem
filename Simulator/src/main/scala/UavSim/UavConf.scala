package UavSim

import com.typesafe.config.ConfigFactory

trait UavConf {
  // config object
  val conf = ConfigFactory.load()

  // UAV config
  lazy val WAITBEGIN = conf.getInt("uav.waitBegin")
  lazy val WAITCONTINUE = conf.getInt("uav.waitContinue")



}
