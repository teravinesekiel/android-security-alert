package com.teraglobal.security_alert.utils

class AppException(var code: String, override var message: String) : Exception(
    message
)
