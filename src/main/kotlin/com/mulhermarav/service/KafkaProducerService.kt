package com.mulhermarav.service

import com.mulhermarav.model.Message
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Message>
) {

    fun sendMessage(topic: String, message: Message) {
        kafkaTemplate.send(topic, message)
    }
}