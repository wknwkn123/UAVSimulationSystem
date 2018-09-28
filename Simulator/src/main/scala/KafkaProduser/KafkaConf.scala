package KafkaProduser


import com.typesafe.config.ConfigFactory


trait KafkaConf {
  // config object
  val conf = ConfigFactory.load()

  // kafka config
  lazy val KAFKAHOST = conf.getString("kafka.host")
  lazy val KAFKAPORT = conf.getInt("kafka.port")
  lazy val KAFKATOPIC = conf.getString("kafka.topic")
  lazy val WAITBEGIN = conf.getInt("uav.waitBegin")
  lazy val WAITCONTINUE = conf.getInt("uav.waitContinue")

}
