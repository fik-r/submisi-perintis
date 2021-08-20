package com.mobile.submissionperintis.network

abstract class UseCase<SuccessType : Any, ErrorType : Any, in Params> {

    abstract suspend fun build(params: Params?): NetworkResponse<SuccessType, ErrorType>

    suspend fun execute(params: Params? = null): NetworkResponse<SuccessType, ErrorType> {
        return build(params)
    }

}

