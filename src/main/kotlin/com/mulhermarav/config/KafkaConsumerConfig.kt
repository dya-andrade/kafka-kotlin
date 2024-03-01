package com.mulhermarav.config

import com.mulhermarav.model.Message
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    @Value("\${boostrap-server.config}")
    lateinit var bootstrapServerConfig: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Message> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9094"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "test-consumers"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), JsonDeserializer(Message::class.java))
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Message> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Message>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}