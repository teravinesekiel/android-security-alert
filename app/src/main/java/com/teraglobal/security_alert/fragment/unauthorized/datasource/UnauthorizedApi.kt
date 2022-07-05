package com.teraglobal.security_alert.fragment.unauthorized.datasource

import com.teraglobal.security_alert.fragment.unauthorized.model.UnauthorizedPagination
import com.teraglobal.security_alert.utils.ApiContract
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

interface UnauthorizedApi {

    @GET(ApiContract.REPORT_UNAUTHORIZED)
    suspend fun fetchUnauthorizedReport(@Query("page") page:Int, @Query("size") size: Int, @Query("sort") sort: String) : UnauthorizedPagination

    @POST(ApiContract.REPORT_UNAUTHORIZED_READ)
    suspend fun sendReadUnauthorized(@Query("trackletId") trackletId: String)
}