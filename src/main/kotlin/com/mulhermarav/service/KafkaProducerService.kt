package com.mulhermarav.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService (private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}