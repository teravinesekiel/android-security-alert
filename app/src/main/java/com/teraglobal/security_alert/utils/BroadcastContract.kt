package com.teraglobal.security_alert.utils


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 02/07/22
 */

abstract class BroadcastContract {

    companion object {
        const val MQTT_BROADCAST = "MQTT-SERVICE"
        const val CONNECTED = "CONNECTED"
        const val ERROR = "ERROR"
        const val NEW_UNAUTHORIZED = "NEW_UNAUTHORIZED"
    }
}