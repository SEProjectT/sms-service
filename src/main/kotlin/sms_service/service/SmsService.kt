package sms_service.service

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import sms_service.dto.PhoneMessageDto
import kotlin.io.encoding.ExperimentalEncodingApi


@Service
class SmsService {

    @Value("\${sms.key}")
    private lateinit var key: String

    @Value("\${sms.secret}")
    private lateinit var secret: String

    @OptIn(ExperimentalEncodingApi::class)
    fun send(messageDto: PhoneMessageDto) {
        val consumer = OkHttpOAuthConsumer(key, secret)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(SigningInterceptor(consumer))
            .build();

        val json = JSONObject()

        json.put("sender", "Notificator")
        json.put("message", messageDto.message)
        json.put(
            "recipients", JSONArray().put(
                JSONObject().put("msisdn", 79822583576)
            )
        )

        val body = json.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request: Request = consumer
            .sign(
                Request.Builder()
                    .url("https://gatewayapi.com/rest/mtsms")
                    .post(body)
                    .build()
            )
            .unwrap() as Request

        client.newCall(request).execute().use { response ->
            println(
                response.body!!.string()
            )
        }
    }
}