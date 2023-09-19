package com.mulhermarav.service

import com.mulhermarav.model.Message
import com.mulhermarav.repository.MessageRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService(private val repository: MessageRepository) {

    @KafkaListener(topics = ["test-topic"], groupId = "test-consumers")
    fun consumeMessage(message: String) {
        val entity = repository.save(Message(1, message))

        println("Received Message in group test-consumers: $entity")
    }
}
