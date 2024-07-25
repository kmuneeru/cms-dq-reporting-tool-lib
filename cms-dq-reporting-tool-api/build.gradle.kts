plugins {
    id("java")
    id("org.springframework.boot")
}

group = "tech.paramount.cmsdq"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.validation)
    implementation(libs.spring.boot.jpa)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.web)
    implementation(libs.spring.context)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.jsr310)
    implementation(libs.jakarta.api)
    implementation(libs.modelmapper)
    implementation(libs.mysql)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.test {
    useJUnitPlatform()
}