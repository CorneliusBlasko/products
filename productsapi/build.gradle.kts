plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.jpa") version "2.1.0"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "org.alberto.mut"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // TEST DEPENDENCIES
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // INTEGRATION TESTING DEPENDENCIES
    testImplementation("org.testcontainers:testcontainers:1.20.6")
    testImplementation("org.testcontainers:junit-jupiter:1.20.6")
    testImplementation("org.testcontainers:postgresql:1.20.6")

    // JPA DEPENDENCIES
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.postgresql:postgresql:42.7.5")

    // MAPPING DEPENDENCIES
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // OPENAPI DEPENDENCIES
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}