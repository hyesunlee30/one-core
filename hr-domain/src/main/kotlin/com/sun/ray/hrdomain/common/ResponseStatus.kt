package com.sun.ray.hrdomain.common

enum class ResponseStatus(
    val code: Long?
) {
    SUCCESS(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    NOT_FOUNDED(404),
    CONFLICT(409),
    INTERNAL_SERVER_ERROR(500),

    /*custom error code*/
    FAIL(null);
}