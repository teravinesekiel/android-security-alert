package com.example.security_alert.utils


import com.example.security_alert.BuildConfig

import timber.log.Timber

class AppLog {
    companion object {
        fun e(e: Exception): String {
            return if (BuildConfig.DEBUG) {
                Timber.e(e)
                e.stackTraceToString()
            } else {
                ""
            }
        }
    }
}