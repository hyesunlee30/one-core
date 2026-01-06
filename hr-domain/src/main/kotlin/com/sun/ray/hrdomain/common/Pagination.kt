package com.sun.ray.hrdomain.common

import java.io.Serializable

data class Pagination(
    val totalCount: Int = 0,
    val count: Int = 0,
    val pageNumber: Int = 1,
    val pageSize: Int = 0,
    val sortCode: String? = null
): Serializable