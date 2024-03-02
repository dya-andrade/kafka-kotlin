package com.mulhermarav.service

import com.mulhermarav.model.Message
import com.mulhermarav.repository.MessageRepository
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class KafkaConsumerServiceTest : ShouldSpec({
    val repository = mockk<MessageRepository>()
    val consumer = KafkaConsumerService(repository)

    val message = Message(
        id = 1,
        message = "test"
    )

    context("Kafka Consumer Service Test") {

        should("process message correctly") {

            every {
                repository.save(message)
            } returns message

            consumer.dltMessage("test-topic", message)

            verify {
                repository.save(message)
            }
        }
    }
})
