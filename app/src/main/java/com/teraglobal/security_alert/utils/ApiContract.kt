package com.teraglobal.security_alert.utils


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

abstract class ApiContract {

    companion object {
        const val REPORT_UNAUTHORIZED = "api/v1/report_unauthorized_person"
        const val REPORT_UNAUTHORIZED_READ = "api/v1/report_unauthorized_person/is_read"
    }

}