package com.teraglobal.security_alert.fragment.unauthorized.model

import com.google.gson.annotations.SerializedName


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

data class UnauthorizedPagination (

    @SerializedName("content")
    var content: List<UnauthorizedContent>,

    @SerializedName("last")
    var last: Boolean,

    @SerializedName("totalPages")
    var totalPages: Int,

    @SerializedName("numPages")
    var numPages: Int

)


