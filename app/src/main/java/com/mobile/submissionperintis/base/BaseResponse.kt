package com.mobile.submissionperintis.base

import com.google.gson.Gson

interface ResponseBodyInterface {
    fun toErrorJson(status: String, message: String): String
}

data class ResponseError(
    val status: String,
    val message: String
): ResponseBodyInterface {
    override fun toErrorJson(status: String, message: String): String {
        return Gson().toJson(ResponseError(status = status, message = message))
    }
}