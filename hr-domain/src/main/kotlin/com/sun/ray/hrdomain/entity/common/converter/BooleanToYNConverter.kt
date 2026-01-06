package com.sun.ray.hrdomain.entity.common.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = false)
class BooleanToYNConverter : AttributeConverter<Boolean, String> {

    /**
     * Boolean 값을 DB에 저장할 'Y' 또는 'N'으로 변환
     */
    override fun convertToDatabaseColumn(attribute: Boolean?): String {
        return if (attribute != null && attribute) "Y" else "N"
    }

    /**
     * DB에서 읽어온 'Y' 또는 'N'을 Boolean으로 변환
     */
    override fun convertToEntityAttribute(dbData: String?): Boolean {
        return "Y".equals(dbData, ignoreCase = true)
    }
}