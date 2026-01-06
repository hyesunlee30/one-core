package com.sun.ray.hrdomain.repository.projectsite

import com.sun.ray.hrdomain.entity.projectsite.ProjectSite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectSiteRepository : JpaRepository<ProjectSite, Long> {
    fun findBySiteCode(siteCode: String): ProjectSite
}