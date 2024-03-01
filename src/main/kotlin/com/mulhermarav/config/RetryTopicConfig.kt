package com.mulhermarav.config

import com.mulhermarav.model.Message
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.retrytopic.RetryTopicConfiguration
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder

@Configuration
class RetryTopicConfig {

    @Bean
    fun retryTopicConfiguration(kafkaTemplate: KafkaTemplate<String, Message>): RetryTopicConfiguration {
        return RetryTopicConfigurationBuilder
            .newInstance()
            .dltHandlerMethod("KafkaCustomDltProcessor","processDltMessage")
            .maxAttempts(3)
            .create(kafkaTemplate)
    }
}