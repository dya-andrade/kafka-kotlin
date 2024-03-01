package com.mulhermarav.controller

import com.mulhermarav.model.Message
import com.mulhermarav.repository.MessageRepository
import com.mulhermarav.service.KafkaProducerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kafka")
class KafkaController(
    private val kafkaProducerService: KafkaProducerService,
    private val repository: MessageRepository
) {

    @PostMapping("/send")
    fun sendMessageToKafka(@RequestBody message: Message) {
        kafkaProducerService.sendMessage("test-topic", message)
    }

    @GetMapping("/messages")
    fun consumeKafkaMessage(): MutableList<Message> {
        return repository.findAll()
    }
}