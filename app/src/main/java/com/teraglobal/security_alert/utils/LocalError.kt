package com.teraglobal.security_alert.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocalError(

    var code: String,

    var messageId: Int,

    var message: String,

    var stackTrace: String = ""

    ):Parcelable
