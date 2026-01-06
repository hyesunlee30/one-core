package com.sun.ray.hrdomain.entity.common.codes

enum class GenderCodes (
    val actualValues: String?,
    val description: String?
) {
    //성별
    ALL_GENDER("G00", "전체 성별"),
    MALE("G01", "남성",),
    FEMALE("G02", "여성");

    companion object {
        fun convertFromCodes(actualValues: String?): GenderCodes =
            values().first { it.actualValues == actualValues }

        fun findActualValuesByCode(code: String?): String? =
            if (code == null) ALL_GENDER.actualValues
            else values().firstOrNull { it.toString() == code }?.actualValues

    }

}
