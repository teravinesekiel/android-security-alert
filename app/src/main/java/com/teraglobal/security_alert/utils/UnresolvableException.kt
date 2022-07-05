package com.teraglobal.security_alert.utils

import com.teraglobal.security_alert.R


/**
 * Created by esekiel on 09/02/2021.
 */

class UnresolvableException : Exception {

    var code: String

    var messageStringId: Int

    constructor(code: String, message: String, messageStringId: Int) : super(message) {
        this.code = code
        this.messageStringId = messageStringId
    }

    constructor(code: String, message: String) : super(message) {
        this.code = code
        this.messageStringId = R.string.default_error
    }

}