package com.teraglobal.security_alert.fragment.unauthorized.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

@Parcelize
data class UnauthorizedContent(

    @SerializedName("watchlistMemberId")
    var watchlistMemberId: String,

    @SerializedName("timeAppeared")
    var timeAppeared: String,

    @SerializedName("timeDisappeared")
    var timeDisappeared: String,

    @SerializedName("watchlistMemberName")
    var watchlistMemberName: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("location")
    var location: String,

    @SerializedName("videoRtsp")
    var videoRtsp: String,

    @SerializedName("trackletId")
    var trackletId: String

    ) : Parcelable