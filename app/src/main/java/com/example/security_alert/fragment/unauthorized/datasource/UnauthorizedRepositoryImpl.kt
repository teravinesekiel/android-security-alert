package com.example.security_alert.fragment.unauthorized.datasource

import com.example.security_alert.fragment.unauthorized.model.UnauthorizedPagination
import com.example.security_alert.utils.Result
import javax.inject.Inject


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

class UnauthorizedRepositoryImpl @Inject constructor(
    private val dataSource: UnauthorizedDataSource
) : UnauthorizedRepository {
    override suspend fun getUnauthorizedReport(page: Int, size: Int): Result<UnauthorizedPagination> {
        return dataSource.getUnauthorizedReport(page, size)
    }

    override suspend fun sendReadUnauthorizedReport(trackletId: String): Result<Unit> {
        return dataSource.sendReadUnauthorizedReport(trackletId)
    }
}