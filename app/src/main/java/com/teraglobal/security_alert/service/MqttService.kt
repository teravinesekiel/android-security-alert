package com.teraglobal.security_alert.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.teraglobal.security_alert.MainActivity
import com.teraglobal.security_alert.R
import com.teraglobal.security_alert.utils.BroadcastContract
import com.teraglobal.security_alert.utils.MQTTContract
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import timber.log.Timber


class MqttService : Service() {

    private lateinit var client: MqttAndroidClient

    companion object{
        const val CHANNEL_ID = "ALERT_CHANNEL_01"
        const val CHANNEL_NAME = "Security Alert"
        const val CHANNEL_DESCRIPTION = "New unauthorized person detected"
    }

    override fun onCreate() {
        super.onCreate()
        initMqttService()
    }

    private fun initMqttService() {
        val clientId = MqttClient.generateClientId()
        val options = MqttConnectOptions()
        options.userName = MQTTContract.MQTT_SERVER_URI_USER
        options.password = MQTTContract.MQTT_SERVER_URI_PWD.toCharArray()

        options.isCleanSession = true
        options.keepAliveInterval = 30

        client = MqttAndroidClient(this, MQTTContract.MQTT_SERVER_URI, clientId);
        client.setCallback(MQTTCallback())
        client.connect(options, null, MqttActionListener())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (client != null && client.isConnected) {
            sendLocalBroadCast("", BroadcastContract.CONNECTED)
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    inner class MQTTCallback : MqttCallback {
        override fun connectionLost(cause: Throwable) {
            cause.printStackTrace()
            Timber.i("Server connection lost")
        }

        override fun messageArrived(topic: String, message: MqttMessage) {
            if (topic == MQTTContract.MQTT_UNAUTHORIZED_CHANNEL) {
                createNotification()
            } else if (topic == MQTTContract.MQTT_UNAUTHORIZED_READ_CHANNEL) {
                sendLocalBroadCast("", BroadcastContract.NEW_UNAUTHORIZED)
            }

            Timber.i("messageArrivedFrom: $topic")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Timber.i("deliveryComplete: $token")
        }
    }

    private fun createNotification() {
        val notificationManager : NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (mChannel == null) {
                mChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
                mChannel.description = CHANNEL_DESCRIPTION
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notificationManager.createNotificationChannel(mChannel)
            }

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cctv)
                .setContentTitle(CHANNEL_NAME)
                .setContentText(CHANNEL_DESCRIPTION)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
                .setDefaults(Notification.DEFAULT_ALL)

            notificationManager.notify(1002, builder.build())
        }

        sendLocalBroadCast("", BroadcastContract.NEW_UNAUTHORIZED)


    }

    inner class MqttActionListener : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            if (client.isConnected) {
                Timber.i("Server connection success")
                val topics = arrayOf(
                    MQTTContract.MQTT_UNAUTHORIZED_CHANNEL,
                    MQTTContract.MQTT_UNAUTHORIZED_READ_CHANNEL,
                )
                val qos = intArrayOf(MQTTContract.MQTT_QOS_2, MQTTContract.MQTT_QOS_2)
                client.subscribe(topics, qos)
                sendLocalBroadCast("", BroadcastContract.CONNECTED)
            }
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
            Timber.e("Failure Listener: " + exception.localizedMessage)
            val message =
                "Connection failure to " + MQTTContract.MQTT_SERVER_URI + " " + exception.localizedMessage
            sendLocalBroadCast(message, BroadcastContract.ERROR)
        }
    }

    private fun sendLocalBroadCast(message: String, status: String) {
        val intent = Intent(BroadcastContract.MQTT_BROADCAST)
        intent.putExtra("status", status)
        intent.putExtra("message", message)
        applicationContext.sendBroadcast(intent)
    }
}