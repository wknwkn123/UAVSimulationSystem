Setup dockerized Kafka Service

* Please replace the used IP in example commands with your machine IP.

1. Run Zookeeper

  docker run -d \
  --name zookeeper \
  -p 2181:2181 \
  jplock/zookeeper

2. Run Kafka Service

  docker run -d \
  --name kafka \
  -p 7203:7203 \
  -p 9092:9092 \
  -e KAFKA_ADVERTISED_HOST_NAME=155.69.147.231 \
  -e ZOOKEEPER_IP=155.69.147.231 \
  ches/kafka

 3. Create topic

  docker run \
  --rm ches/kafka kafka-topics.sh \
  --create \
  --topic mavz \
  --replication-factor 1 \
  --partitions 1 \
  --zookeeper 155.69.147.231:2181

4. Test Services
  4.1 List topics

    docker run \
    --rm ches/kafka kafka-topics.sh \
    --list \
    --zookeeper 155.69.147.231:2181

  4.2 Produzer

    docker run --rm --interactive \
    ches/kafka kafka-console-producer.sh \
    --topic mavz \
    --broker-list 155.69.147.231:9092

  4.3 Consumer

    docker run --rm \
    ches/kafka kafka-console-consumer.sh \
    --topic mavz \
    --from-beginning \
    --zookeeper 155.69.147.231:2181
