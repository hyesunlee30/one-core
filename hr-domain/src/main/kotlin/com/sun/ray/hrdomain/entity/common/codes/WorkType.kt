package com.sun.ray.hrdomain.entity.common.codes

enum class WorkType(
    val actualValues: String?,
    val description: String?
) {
    REGULAR("R","정규직"),
    FREE("F","프리랜서"),
    SEMI_FREE("SF","반프리")
}