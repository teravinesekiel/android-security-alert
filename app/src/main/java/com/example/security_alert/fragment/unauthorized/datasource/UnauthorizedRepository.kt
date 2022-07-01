package com.example.security_alert.fragment.unauthorized.datasource

import com.example.security_alert.fragment.unauthorized.model.UnauthorizedPagination
import com.example.security_alert.utils.Result


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

interface UnauthorizedRepository {

    suspend fun getUnauthorizedReport(page: Int, size: Int): Result<UnauthorizedPagination>

    suspend fun sendReadUnauthorizedReport(trackletId: String): Result<Unit>
}