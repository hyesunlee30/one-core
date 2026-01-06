package com.sun.ray.hrdomain.repository.jobposition

import com.sun.ray.hrdomain.entity.jobposition.JobPosition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobPositionRepository : JpaRepository<JobPosition, Long> {
    fun findByPositionCode(positionCode: String): JobPosition
}