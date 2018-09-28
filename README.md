UAV Simulation Sysstem

  Main Services

    WebService
      Web React Client and Node Webserver

    MapBuilder
      Uav Waypoint Map and Flight path generation ervice

    Simulator
      Simulate Ffying UAV's Acoording to the received flight paths from 'MapBuilder' service.
      Produze drone Data to a Kafka Topic.

    Consumer
      Consumes data from Kafka Topin on request of Web Client.

    KafkaBroker
      Dockerized Kafka Message Broker.
      Zookeeper to manage Kafka Services.
 
 Please refer the README.md files inside each service to setup.
