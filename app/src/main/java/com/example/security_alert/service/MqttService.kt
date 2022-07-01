package com.example.security_alert.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.security_alert.utils.MQTTContract
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import kotlin.text.Charsets.UTF_8

class MqttService : Service() {
    companion object {
        private const val MQTT_HOST = "192.168.1.8"
        private const val MQTT_PORT = 1883
        private const val MQTT_USERNAME = "mqtt"
        private const val MQTT_PASSWORD = "Sm4rtch4rgEr"

        const val INTENT_FILTER = "mqttIntentFilter"
    }

    private lateinit var client: MqttAndroidClient
    private val broadcastIntent by lazy { Intent(INTENT_FILTER) }

    override fun onCreate() {
        super.onCreate()
        initMqtt()
    }

    @Throws(MqttException::class)
    private fun initMqtt() {
        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(
            this.applicationContext, "tcp://$MQTT_HOST:$MQTT_PORT",
            clientId, MemoryPersistence()
        )
        val options = MqttConnectOptions()
        options.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1
        options.userName = MQTT_USERNAME
        options.password = MQTT_PASSWORD.toCharArray()
        options.keepAliveInterval = 30
        options.isCleanSession = true
        client.setCallback(MQTTCallback())
        client.connect(
            options,
            null,
            MqttActionListener()
        )
        broadcastIntent.putExtra("status","MQTT Connecting")
        LocalBroadcastManager.getInstance(this@MqttService).sendBroadcast(broadcastIntent)
    }

    private fun initMqttService() {
        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(this, MQTTContract.MQTT_SERVER_URI, clientId);
        //set callback
        val options = MqttConnectOptions()
        options.userName = MQTTContract.MQTT_SERVER_URI_USER
        options.password = MQTTContract.MQTT_SERVER_URI_PWD.toCharArray()
        options.keepAliveInterval = 30
        options.isCleanSession = true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this@MqttService.javaClass.name, "onDestroy: MQTT Service Destroyed")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY;
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    inner class MQTTCallback : MqttCallback {
        override fun connectionLost(cause: Throwable) {
            cause.printStackTrace()
        }

        override fun messageArrived(topic: String, message: MqttMessage) {
            val messageString = String(message.payload, UTF_8)

            broadcastIntent.putExtra(topic,messageString)
            LocalBroadcastManager.getInstance(this@MqttService).sendBroadcast(broadcastIntent)
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(this@MqttService.javaClass.name, "deliveryCompleteToken: $token")
        }
    }

    inner class MqttActionListener : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.d(this@MqttService.javaClass.name, "onSuccess: MQTT connect success")
            client.subscribe(arrayOf("floor","base64","floor/unauthorized","base64/unauthorized"), intArrayOf(0,0,0,0),null,null)

            broadcastIntent.putExtra("status","Connect Mqtt Success")
            LocalBroadcastManager.getInstance(this@MqttService).sendBroadcast(broadcastIntent)
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
            Log.d(this@MqttService.javaClass.name, "onFailure: Failed to connect mqtt")
            exception.printStackTrace()

            broadcastIntent.putExtra("status","Failed to connect mqtt")
            LocalBroadcastManager.getInstance(this@MqttService).sendBroadcast(broadcastIntent)
        }
    }
}