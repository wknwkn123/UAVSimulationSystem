package main

import (
    "fmt"
    "log"
    "os"
    "time"
    //"websocket"
    "net/http"
    "github.com/lib/gorilla/websocket"

    "github.com/lib/Shopify/sarama"
    "github.com/lib/wvanbergen/kafka/consumergroup"
)

var upgrader = websocket.Upgrader{
  ReadBufferSize:    4096,
	WriteBufferSize:   4096,
	EnableCompression: true,
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

const (
    zookeeperConn = "155.69.147.231:2181"
    cgroup = "mavgroup3"
    topic = "mavz"
)

func main() {

    // setup sarama log to stdout
    sarama.Logger = log.New(os.Stdout, "", log.Ltime)

    // init consumer
    fmt.Println("Init consumer ...")
    cg, err := initConsumer()
    if err != nil {
        fmt.Println("Error consumer goup: ", err.Error())
        os.Exit(1)
    }
    defer cg.Close()

    // run consumer + ws
    wsServer(cg)

}

func wsServer(cg *consumergroup.ConsumerGroup)  {
      fmt.Println(" WS Server starting...")
      http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
      conn, _ := upgrader.Upgrade(w, r, nil) // error ignored for sake of simplicity

      for {
        // Read message from browser
        msgType, msg, err := conn.ReadMessage()
        if err != nil {
          return
        }

        // Print the message to the console
        fmt.Printf("%s sent: %s\n", conn.RemoteAddr(), string(msg),msgType)
        consume(cg, conn, err)
        // Write message back to browser
         if err = conn.WriteMessage(msgType, msg); err != nil {
           return
         }
      }
    })


    http.HandleFunc("/root", func(w http.ResponseWriter, r *http.Request) {
      fmt.Println("Server root .... ")
      http.ServeFile(w, r, "websockets.html")
    })

    fmt.Println(" Server is up @ port 9001")
    http.ListenAndServe(":9001", nil)



}

func initConsumer()(*consumergroup.ConsumerGroup, error) {
    // consumer config
    config := consumergroup.NewConfig()
    config.Offsets.Initial = sarama.OffsetNewest //OffsetOldest
    config.Offsets.ProcessingTimeout = 1 * time.Second

    // join to consumer group
    cg, err := consumergroup.JoinConsumerGroup(cgroup, []string{topic}, []string{zookeeperConn}, config)
    if err != nil {
        return nil, err
    }

    return cg, err
}

func consume(cg *consumergroup.ConsumerGroup, conn *websocket.Conn, err error) {
  //var data[10] string
    count := 0
    for {
        select {
        case msg := <-cg.Messages():
            // messages coming through chanel
            // only take messages from subscribed topic
      	    if msg.Topic != topic {
                      continue
                  }

            //fmt.Println("Topic: ", msg.Topic)
            count++
            //print message count with client address
            fmt.Println(conn.RemoteAddr(), " -> ", count)
            //send data to web client through web socket
            conn.WriteMessage(1, msg.Value)

            ///error handle
            if err = conn.WriteMessage(1, msg.Value); err != nil {
             return
            }

            // commit to zookeeper that message is read
            // this prevent read message multiple times after restart
            err := cg.CommitUpto(msg)
            if err != nil {
                fmt.Println("Error commit zookeeper: ", err.Error())
            }
        }
    }
}
