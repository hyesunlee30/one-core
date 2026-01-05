package com.sun.ray.hrinterface.controller

import com.sun.ray.hrdomain.common.ApplicationResponseDto
import com.sun.ray.hrdomain.common.ConnectionPoolMonitor
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hr/api/v1/check")
class ConnectionCheckController(
    private val connectionPoolMonitor: ConnectionPoolMonitor,
) {

    @GetMapping("/connection-pool")
    @Operation(summary = "DB Connection Pool 상태 조회", description = "Leader 및 Follower DB의 Connection Pool 상태를 반환합니다.")
    fun getConnectionPoolStatus(): ResponseEntity<ApplicationResponseDto<Any>> {
        val connectionStatus = connectionPoolMonitor.getConnectionPoolStatus()

        return ResponseEntity.ok(ApplicationResponseDto.success(connectionStatus))
    }
}