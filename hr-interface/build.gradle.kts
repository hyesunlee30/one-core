java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(project(":hr-domain"))

    implementation("org.springdoc:springdoc-openapi-ui:1.6.15")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("com.google.code.gson:gson")

    // 로깅 라이브러리 (기존 유지)
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.2")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("ch.qos.logback:logback-core:1.2.11")
    implementation("ch.qos.logback:logback-access:1.2.11")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")

    testImplementation(project(":hr-domain"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("com.appmattus.fixture:fixture:1.2.0")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.5")
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}