package com.example.security_alert.fragment.unauthorized_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.security_alert.fragment.unauthorized.model.UnauthorizedContent
import com.example.security_alert.utils.LocalError
import timber.log.Timber
import javax.inject.Inject

class UnauthorizedDetailViewModel @Inject constructor() : ViewModel() {

    private val _rtspUsername = "admin"
    val rtspUsername : String get() = _rtspUsername

    private val _rtspPassword = "admin"
    val rtspPassword : String get() = _rtspPassword

    private val _unauthorized = MutableLiveData<UnauthorizedContent>()
    val unauthorized : LiveData<UnauthorizedContent> get() = _unauthorized


    fun setUnauthorized(unauthorizedContent: UnauthorizedContent){
        _unauthorized.value = unauthorizedContent
    }

    fun getRtspUrl(): String {
        return _unauthorized.value?.videoRtsp!!
    }
}