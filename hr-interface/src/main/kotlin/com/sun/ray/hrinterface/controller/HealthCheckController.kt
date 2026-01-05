package com.sun.ray.hrinterface.controller

import com.sun.ray.hrdomain.common.ApplicationResponseDto
import com.sun.ray.hrdomain.common.ResponseStatus
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController(

) {

    @GetMapping(value = ["/"])
    @Operation(summary = "서버 상태 조회", description = "")
    fun initialize(): ResponseEntity<ApplicationResponseDto<String?>> {
        return ResponseEntity.ok().body(
            ApplicationResponseDto(
                ResponseStatus.SUCCESS,
                null,
                null,
                null,
                null
            )
        )
    }
}