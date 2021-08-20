package com.mobile.submissionperintis.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mobile.submissionperintis.network.NetworkResponse

open class BaseViewModel : ViewModel() {
    internal fun NetworkResponse<Any, ResponseError>.setErrorResponse(
        onServerError: (code: Int, message: String) -> Unit,
        onNetworkError: (Exception) -> Unit,
        onUnKnowError: ((Throwable) -> Unit)? = null
    ) {
        if (this is NetworkResponse.Success) return
        when (this) {
            is NetworkResponse.ServerError -> {
                onServerError(this.code, this.body?.message ?: "")
            }
            is NetworkResponse.NetworkError -> onNetworkError(this.error)
            is NetworkResponse.UnknownError -> {
                Log.e(this.javaClass.simpleName, this.error.message ?: "")
                onUnKnowError?.invoke(this.error)
            }
        }
    }
}