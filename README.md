
# 📑 One-Core HR Project

**One-Core HR**은 Oracle Database를 기반으로 한 **급여 및 근태 관리 중심의 인사 관리(HR) 시스템**입니다.
Kotlin과 Spring Boot를 활용하여 멀티 모듈 기반의 견고한 Backend API를 구축하고, Docker Compose를 통해 인프라 설정 없이 즉시 실행 가능한 환경을 제공합니다.

## 📌 Project Goals

* **Multi-Module Architecture**: Domain과 Interface 레이어를 분리하여 모듈 간 독립성 확보.
* **Database Routing**: `@Transactional(readOnly = true)` 여부에 따른 Leader/Follower DB 동적 분기.
* **Infrastructure Automation**: Docker를 이용한 Oracle, Redis, MongoDB의 통합 환경 구축.
* **Security & Reliability**: Jasypt를 이용한 설정 암호화 및 Kotlin의 Null 안정성 활용.

---

## 🛠 Tech Stack

### Framework & Language

* **Language**: Kotlin 1.5.21
* **Framework**: Spring Boot 2.5.4
* **Build Tool**: Gradle (Kotlin DSL)
* **JDK**: 11

### Data & Infrastructure

* **Database**: Oracle Database XE 11g (Docker)
* **NoSQL**: Redis (Cache), MongoDB
* **Persistence**: Spring Data JPA & QueryDSL 4.4.0
* **API Documentation**: Springfox Swagger UI 3.0.0

---

## 🧩 Core Features (Scope)

* **인사(HR) 관리**: 사원 기본 정보 및 조직 체계 관리.
* **커넥션 모니터링**: Leader 및 Follower DB의 Connection Pool 상태 실시간 조회.
* **확장성**: 향후 근태(Attendance), 급여(Payroll) 모듈 확장을 고려한 도메인 설계.

---

## 🐳 Docker Infrastructure

본 프로젝트는 Docker Compose를 통해 인프라를 통합 관리합니다.

### 1. Database (Oracle XE)

* **Image**: `gvenzl/oracle-xe:11`
* **Port**: `1521` (SID: `XE`)
* **User/Pass**: `onecore` / `onecore`

### 2. NoSQL & Middleware

* **Redis**: `6.2-alpine` (Port: `6379`)
* **MongoDB**: `4.4` (Port: `27017`)

---

## ▶️ Getting Started (실행 방법)

### 1️⃣ 빌드 (Build)

```bash
./gradlew clean build

```

### 2️⃣ 실행 (Run)

로컬 환경에서는 `local` 프로필을 활성화하여 실행합니다.

```bash
java -jar -Dspring.profiles.active=local hr-interface/build/libs/hr-interface-latest.jar

```

### 3️⃣ 접속 정보 (Access)

* **Swagger API Docs**: [http://localhost:8080/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html)
* **Connection Check**: `/hr/api/v1/check/connection-pool`

---

## ⚙️ Key Configuration (핵심 설정)

* **Database Routing**: `AbstractRoutingDataSource`와 `LazyConnectionDataSourceProxy`를 이용한 부하 분산.
* **All-Open & No-Arg**: JPA 엔티티의 프록시 생성 및 기본 생성자 지원을 위해 Kotlin 플러그인 적용.
* **QueryDSL**: `kapt`를 이용한 QClass 생성 및 타입 안정적인 쿼리 환경 구축.

