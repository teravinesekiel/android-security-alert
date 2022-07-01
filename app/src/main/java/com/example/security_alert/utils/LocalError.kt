package com.example.security_alert.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*

@Parcelize
data class LocalError(

    var code: String,

    var messageId: Int,

    var message: String,

    var stackTrace: String = ""

    ):Parcelable
