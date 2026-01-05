package com.sun.ray.hrdomain.common

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ConnectionPoolMonitor(
    @Qualifier("leaderDataSource") private val leaderDataSource: HikariDataSource,
    @Qualifier("followerDataSource") private val followerDataSource: HikariDataSource
) {
    fun getConnectionPoolStatus(): Map<String, Map<String, Int>> {
        return mapOf(
            "LeaderDB" to getPoolStatus(leaderDataSource),
            "FollowerDB" to getPoolStatus(followerDataSource)
        )
    }

    private fun getPoolStatus(dataSource: HikariDataSource): Map<String, Int> {
        return mapOf(
            "Active" to dataSource.hikariPoolMXBean.activeConnections, // 현재 실제로 쿼리를 수행 중인 커넥션 수.
            "Idle" to dataSource.hikariPoolMXBean.idleConnections, //Idle: 풀에서 대기 중인 유휴 커넥션 수
            "Total" to dataSource.hikariPoolMXBean.totalConnections,
            "Pending" to dataSource.hikariPoolMXBean.threadsAwaitingConnection // 커넥션을 얻기 위해 대기 중인 스레드 수 (이 수치가 높으면 DB 병목)
        )
    }
}