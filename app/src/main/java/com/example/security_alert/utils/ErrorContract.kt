package com.example.security_alert.utils

/**
 * Created by esekiel on 09/02/2021.
 */
abstract class ErrorContract {

    companion object {

        const val UNKNOWN = "LCL100"

        const val ERROR_TIMEOUT = "LCL001"

        const val ERROR_HTTP_ERROR_CODE_NOT_AVAILABLE = "LCL002"

        const val ERROR_HTTP_EMPTY_BODY = "LCL003"

        const val DEVICE_ROOTED = "LCL004"

        const val VASCO_IO_ERROR = "LCL006"

        const val CURSOR_NPE = "LCL007"

        const val ISPRINT_PRE_AUTH = "LCL008"

        const val NPE = "LCL009"

        const val LOCATION_SETTING_ERROR = "LCL012"

        const val LOCATION_NOT_AVAILABLE = "LCL013"

        const val WALLET_NOT_FOUND = "LCL014"

        const val SECURE_STORAGE = "LCL015"

        const val DEVICE_BINDING = "LCL016"

        const val WHITEBOX_CRYPTOGRAPHY = "LCL017"

        const val WHITEBOX_CRYPTOGRAPHY_ENCODING = "LCL031"

        const val ERROR_LIVENESS_SCORE_NOT_FULFILLED = "LCL032"

        const val PASSIVE_LIVENESS_BELOW_THRESHOLD = "LCL033"

        const val QR_CAMERA_INIT_FAILED = "LCL034"

        const val QR_CAMERA_PERMISSION_REQUIRED = "LCL035"

        const val QR_CAMERA_ACCESS_FAILED = "LCL036"

        const val MAX_RETRY_REACHED = "LCL037"

        const val NETWORK_ERROR = "LCL038"

        const val UNAUTHORIZED = "LCL039"

        const val FAILED_TO_READ_FILE = "LCL040"

        const val NOT_FOUND = "LCL041"

        const val QR_EXPIRED = "LCL042"

        const val TIME_OUT = "LCL043"

        const val CONNECT_ERROR = "LCL044"

        const val DOCUMENT_NOT_RECOGNIZED = "LCL045"

        const val KTP_DOES_NOT_MATCH = "LCL046"
    }

}