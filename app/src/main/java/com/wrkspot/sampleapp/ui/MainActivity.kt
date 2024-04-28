package com.wrkspot.sampleapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.wrkspot.sampleapp.databinding.ActivityMainBinding
import com.wrkspot.sampleapp.utils.Utility
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.UUID


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AndroidMqttClient"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var mqttClient: Mqtt5Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setDate()

        initMqttClient()
    }

    override fun onResume() {
        super.onResume()
        val response = connect()
        Log.d(TAG, "onResume: mqtt connect response:$response")
        if (response == "SUCCESS") {

            mqttClient.toAsync().subscribeWith()
                .topicFilter("test/topic")
                .callback { publish ->
                    val topic = publish.topic.toString()
                    Log.d(TAG, "subscribed topic: $topic")
                    val payload = publish.payloadAsBytes.decodeToString()
                    Log.d(TAG, "subscribed payload: $payload")
                }
                .send()

            mqttClient.toBlocking().publishWith()
                .topic("test/topic")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload("sample payload".encodeToByteArray())
                .send()
        }
    }

    private fun initMqttClient() {
        mqttClient = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("6e25770308f4476ab9e0234456f139bb.s1.eu.hivemq.cloud")
            .serverPort(8883)
            .sslWithDefaultConfig()
            .simpleAuth()
            .username("sadashiv")
            .password("Sadashiv@123".encodeToByteArray())
            .applySimpleAuth()
            .addConnectedListener {
                Log.d(TAG, "connected successfully")
            }
            .addDisconnectedListener {
                Log.d(TAG, "disconnected")
            }
            .build()
    }

    @SuppressLint("SetTextI18n")
    private fun setDate() {
        val formattedDate = Utility.formatDateToString(Date(), "dd MMM hh:mm a", "GMT-07")
        binding.subTitle.text = "$formattedDate PST"
    }

    private fun connect(): String {
        return try {
            val connAckMessage = mqttClient.toBlocking().connect()
            connAckMessage.reasonCode.toString()
        } catch (e: Exception) {
            e.message ?: ""
        }
    }

    override fun onPause() {
        super.onPause()
        mqttClient.toBlocking().disconnect()
    }

}