package com.sun.ray.hrdomain.entity.projectsite

import com.sun.ray.hrdomain.entity.common.converter.BooleanToYNConverter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "project_site")
@SequenceGenerator(
    name = "project_site_generator",
    sequenceName = "PROJECT_SITE_SEQ", // DB에 생성될 시퀀스 이름
    initialValue = 1,
    allocationSize = 1
)
class ProjectSite(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_site_generator")
    @Column(name = "SITE_ID")
    val id: Long? = null,

    /* 사이트코드 (예: SITE-01) */
    @Column(name = "SITE_CD", nullable = false, unique = true, length = 20)
    var siteCode: String,

    /* 사이트명 (예: 강남 사옥, 여의도 전산센터) */
    @Column(name = "SITE_NM", nullable = false, length = 100)
    var siteName: String,

    /* 부서명 (예: 공공운영부, SI개발1팀) */
    @Column(name = "DEPT_NM", nullable = false, length = 100)
    var departmentName: String, // clientName에서 변경

    /* 주소 */
    @Column(name = "ADDRESS", length = 500)
    var address: String? = null,

    /* 사이트 담당자 연락처 */
    @Column(name = "CONTACT_NUM", length = 50)
    var contactNumber: String? = null,

    /* 사이트 상태 (ACTIVE, COMPLETED, HOLD) */
    @Enumerated(EnumType.STRING)
    @Column(name = "SITE_STATUS", length = 20, nullable = false)
    var siteStatus: SiteStatus = SiteStatus.ACTIVE,

    /* 사용여부 */
    @Column(name = "USE_YN", length = 1)
    @Convert(converter = BooleanToYNConverter::class)
    var isActive: Boolean = true,

    /* 시스템 등록정보 */
    @Column(name = "SYS_REG_DTIME", updatable = false)
    var registrationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_REGR_ID", updatable = false)
    var registrationId: String,

    /* 시스템 수정정보 */
    @Column(name = "SYS_MOD_DTIME")
    var modificationDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "SYS_MODR_ID")
    var modificationId: String
)

enum class SiteStatus(val description: String) {
    ACTIVE("가동 중"),      // 사이트 오픈 및 인력 투입 가능
    COMPLETED("종료"),   // 프로젝트 종료 (조회용)
    HOLD("중단")           // 일시 정지
}