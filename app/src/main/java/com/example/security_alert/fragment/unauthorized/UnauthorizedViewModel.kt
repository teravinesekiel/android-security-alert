package com.example.security_alert.fragment.unauthorized

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.security_alert.R
import com.example.security_alert.fragment.unauthorized.datasource.UnauthorizedRepository
import com.example.security_alert.fragment.unauthorized.datasource.UnauthorizedRepositoryImpl
import com.example.security_alert.fragment.unauthorized.model.UnauthorizedContent
import com.example.security_alert.utils.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class UnauthorizedViewModel @Inject constructor(private val repo: UnauthorizedRepositoryImpl) :
    ViewModel() {

    private var _currentPage = 1;

    private var _isLast = false;


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _unauthorizedReport = MutableLiveData<List<UnauthorizedContent>>()
    val unauthorizedReport: LiveData<List<UnauthorizedContent>> get() = _unauthorizedReport

    private val _unauthorizedContent = MutableLiveData<Event<UnauthorizedContent>>()
    val unauthorizedContent: LiveData<Event<UnauthorizedContent>> get() = _unauthorizedContent

    private val _errorEvent = MutableLiveData<Event<LocalError>>()
    val errorEvent: LiveData<Event<LocalError>>
        get() = _errorEvent

    private val _detailUnauthorizedEvent = MutableLiveData<Event<Unit>>()
    val detailUnauthorizedEvent: LiveData<Event<Unit>>
        get() = _detailUnauthorizedEvent

    init {
        _loading.value = true
    }

    fun requestUnauthorizedReport(isRefresh: Boolean) {
        fetchUnauthorizedReport(isRefresh)
    }

    fun sendReadUnauthorizedReport(content: UnauthorizedContent) {
        sendReadReport(content)
    }

    fun setError(error: LocalError) {
        _errorEvent.value = Event(error)
    }

    private fun sendReadReport(content: UnauthorizedContent) {
        _loading.value = true
        viewModelScope.launch {
            repo.sendReadUnauthorizedReport(content.trackletId).let { result ->
                _loading.value = false
                if (result is Result.Success) {
                    _currentPage = 1
                    _detailUnauthorizedEvent.value = Event(result.data)
                    _unauthorizedContent.value = Event(content)
                } else if (result is Result.Error) {
                    onError(result.exception)
                }
            }
        }
    }

    private fun fetchUnauthorizedReport(isRefresh: Boolean) {
        _loading.value = true
        viewModelScope.launch {
            repo.getUnauthorizedReport(_currentPage, 10).let { result ->
                _loading.value = false
                if (result is Result.Success) {
                    val unauthorizedPagination = result.data
                    _isLast = unauthorizedPagination.last

                    if (isRefresh) {
                        _unauthorizedReport.value = unauthorizedPagination.content
                    } else {
                        _unauthorizedReport.value =
                            _unauthorizedReport.value?.plus(unauthorizedPagination.content)
                                ?: unauthorizedPagination.content
                    }

                } else if (result is Result.Error) {
                    onError(result.exception)
                }
            }
        }
    }

    fun setNextPage(nextPage: Int) {
        _currentPage += nextPage
    }

    fun isScrolled(): Boolean {
        return _isLast || _loading.value ?: false
    }


    private fun onError(exception: Exception) {
        val stackTrace = AppLog.e(exception)
        when (exception) {
            is SocketTimeoutException -> {
                _errorEvent.value = Event(
                    LocalError(ErrorContract.TIME_OUT, R.string.time_out, "", stackTrace)
                )
            }
            is IOException -> {
                _errorEvent.value = Event(
                    LocalError(ErrorContract.NETWORK_ERROR, R.string.network_error, "", stackTrace)
                )
            }
            is AppException -> {
                _errorEvent.value = Event(
                    LocalError(
                        exception.code,
                        0,
                        exception.message,
                        stackTrace
                    )
                )
            }
            is UnresolvableException -> {
                _errorEvent.value = Event(
                    LocalError(
                        exception.code,
                        exception.messageStringId,
                        "",
                        stackTrace
                    )
                )
            }
            else -> {
                _errorEvent.value = Event(
                    LocalError(
                        ErrorContract.UNKNOWN,
                        R.string.default_error,
                        "",
                        stackTrace
                    )
                )
            }
        }
    }

}