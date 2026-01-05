package com.sun.ray.hrdomain.common

data class ApplicationResponseDto<T>(
    val status: ResponseStatus,
    val code: Long? = null,
    val message: String? = null,
    val data: T? = null,
    val pagination: Pagination? = null
) {
    companion object {
        fun <T> success(data: T, pagination: Pagination? = null): ApplicationResponseDto<T> =
            ApplicationResponseDto(
                status = ResponseStatus.SUCCESS,
                data = data,
                pagination = pagination
            )

        fun fail(message: String, code: Long? = null): ApplicationResponseDto<Nothing> =
            ApplicationResponseDto(
                status = ResponseStatus.FAIL,
                message = message,
                code = code
            )
    }
}