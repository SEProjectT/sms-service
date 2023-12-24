package sms_service.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import sms_service.dto.PhoneMessageDto
import sms_service.service.SmsService

@Component
class SmsMessageListener(
    private val objectMapper: ObjectMapper,
    private val smsService: SmsService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["\${topic.sms}"], groupId = "immediate")
    fun listen(payload: ByteArray) {
        val messageDto = objectMapper.readValue(payload, PhoneMessageDto::class.java)

        logger.info("phone message received: {}", messageDto)

        smsService.send(messageDto)
    }
}