package com.mulhermarav.service

import com.mulhermarav.model.Message
import com.mulhermarav.repository.MessageRepository
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService(private val repository: MessageRepository) {

    @RetryableTopic(
        attempts = "1",
        kafkaTemplate = "kafkaTemplate",
        dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR)
    @KafkaListener(
        topics = ["test-topic"],
        groupId = "test-consumers")
    fun dltMessage(@Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, message: Message) {
        val entity = repository.save(message)

        println("Received Message in group test-consumers: $entity and topic: $topic")
    }

    @DltHandler
    fun dltHandler(@Header(KafkaHeaders.RECEIVED_TOPIC) topic: String, message: Message) {
        println("Handling message $message and topic: $topic from DLT")
    }
}
