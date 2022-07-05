package com.teraglobal.security_alert.fragment.unauthorized.datasource

import com.teraglobal.security_alert.fragment.unauthorized.model.UnauthorizedPagination
import com.teraglobal.security_alert.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

class UnauthorizedDataSource constructor(
    private val api: UnauthorizedApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UnauthorizedRepository {
    override suspend fun getUnauthorizedReport(page: Int, size: Int): Result<UnauthorizedPagination> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = api.fetchUnauthorizedReport(page, size, "-dateCreated")
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun sendReadUnauthorizedReport(trackletId: String): Result<Unit> =
        withContext(ioDispatcher) {
            return@withContext try{
                api.sendReadUnauthorized(trackletId)
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}