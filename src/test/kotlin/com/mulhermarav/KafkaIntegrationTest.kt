package com.mulhermarav

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import java.time.Duration
import java.util.*

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    topics = ["test-topic", "test-topic-2"],
    partitions = 1,
    brokerProperties = ["listeners=PLAINTEXT://localhost:9094", "port=9094"]
)
class KafkaIntegrationTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    private lateinit var consumer: KafkaConsumer<String, String>

    @KafkaListener(topics = ["test-topic"], groupId = "test-group")
    fun listen(message: String) {
        println("Received message: $message")
    }

    @Test
    fun testKafkaIntegration() {
        kafkaTemplate.send("test-topic", "test-key", "This is a test!")

        Thread.sleep(1000)

        kafkaTemplate.send("test-topic-2", "test-key-2", "This is a test 2!")

        val props = Properties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9094"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "test-group-2"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name

        consumer = KafkaConsumer(props)
        consumer.subscribe(listOf("test-topic-2"))

        val records = consumer.poll(Duration.ofMillis(1000))
        val messages = records.records("test-topic")

        messages.forEach { msg ->
            println(msg.value())
        }
    }
}
