package com.example.security_alert.fragment.error

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.security_alert.utils.LocalError
import com.example.security_alert.utils.NavUtil
import javax.inject.Inject

class ErrorViewModel @Inject constructor(): ViewModel() {
    private val _localError = MutableLiveData<LocalError>()
    val localError: LiveData<LocalError> = _localError

    private val _result = MutableLiveData<Int>()
    val result: LiveData<Int> = _result

    fun setResultOK() {
        _result.value = NavUtil.RESULT_OK
    }

    fun setLocalError(localError: LocalError){
        _localError.value = localError
    }
}