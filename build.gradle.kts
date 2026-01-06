import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.5.21")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.5.21")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}


plugins {
    id("org.springframework.boot") version "2.5.4" apply false
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jmailen.kotlinter") version "3.7.0"

    kotlin("jvm") version "1.5.21" apply false
    kotlin("kapt") version "1.5.21" apply false
    kotlin("plugin.jpa") version "1.5.21" apply false
    kotlin("plugin.noarg") version "1.5.21" apply false
    kotlin("plugin.spring") version "1.5.21" apply false
    kotlin("plugin.allopen") version "1.5.21" apply false
    kotlin("plugin.serialization") version "1.5.21" apply false
    base
    idea
}


allprojects {
    group = "com.sun.ray"
    version = "latest"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        maxHeapSize = "1024m"
    }

}

subprojects {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
        mavenCentral()
    }

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jmailen.kotlinter")

}
