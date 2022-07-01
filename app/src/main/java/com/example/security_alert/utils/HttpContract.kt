package com.example.security_alert.utils


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

abstract class HttpContract {

    companion object {
        const val DEFAULT_READ_TIMEOUT_IN_SECOND: Long = 3 * 60
        const val DEFAULT_WRITE_TIMEOUT_IN_SECOND: Long = 3 * 60
        const val DEFAULT_CONNECT_TIMEOUT_IN_SECOND: Long = 3 * 60
    }
}