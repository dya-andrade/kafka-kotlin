package com.mulhermarav.service

import com.mulhermarav.model.Message
import com.mulhermarav.repository.MessageRepository
import io.kotest.assertions.throwables.shouldThrow
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

            consumer.dltMessage(message, "test-topic")

            verify {
                repository.save(message)
            }
        }

        should("call dlt method when message processing fails") {

            every {
                repository.save(message)
            } throws RuntimeException("Simulating processing failure")

            val dltProcessor = mockk<KafkaCustomDltProcessor>(relaxed = true)

            verify(exactly = 0) { dltProcessor.processDltMessage(any()) }

            shouldThrow<RuntimeException> {
                consumer.dltMessage(message, "test-topic")
            }

            //consumer.consumeMessage(message, "test-topic")

            verify {
                dltProcessor.processDltMessage(message)
            }
        }
    }



})
