# learn_kafka_with_kotlin
Progress: Develop a REST API with Kafka, MySQL e Kotlin.

* Initialize Kafka and create the topics

````shell
docker-compose up -d --build
````

````shell
docker stop $(docker container ls -q)
````

* Listar tópicos via terminal

````shell
kafka-topics --bootstrap-server localhost:9094 --list
````

