package com.teraglobal.security_alert.utils


import com.teraglobal.security_alert.BuildConfig

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

        fun i(e: Exception): String {
            return if (BuildConfig.DEBUG) {
                Timber.i(e)
                e.stackTraceToString()
            } else {
                ""
            }
        }
    }
}