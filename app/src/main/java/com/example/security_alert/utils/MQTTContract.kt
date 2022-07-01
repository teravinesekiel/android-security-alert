package com.example.security_alert.utils


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 01/07/22
 */

abstract class MQTTContract {

    companion object {
        const val MQTT_SERVER_URI = "tcp://10.106.4.25:1883"
        const val MQTT_SERVER_URI_USER = "tcc"
        const val MQTT_SERVER_URI_PWD = "tcc123";
        const val MQTT_UNAUTHORIZED_CHANNEL = "UnauthorizedChannel"
        const val MQTT_UNAUTHORIZED_READ_CHANNEL = "UnauthorizedReadChannel"
    }
}