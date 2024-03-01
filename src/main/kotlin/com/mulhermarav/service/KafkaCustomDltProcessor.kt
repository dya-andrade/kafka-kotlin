package com.mulhermarav.service

import com.mulhermarav.model.Message
import org.springframework.stereotype.Component

@Component
class KafkaCustomDltProcessor {

    fun processDltMessage(message: Message) {
        println("Processing DLT message: $message")
    }
}