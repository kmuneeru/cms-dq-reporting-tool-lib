plugins {
    id("java")
    id("org.springframework.boot")
}

group = "tech.paramount.cmsdq"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.validation)
    implementation(libs.spring.core)
    implementation(libs.spring.web)
    implementation(libs.spring.context)

    implementation(libs.jackson.module.jsonSchema)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.jsr310)
    implementation(libs.jaxb.api)
    implementation(libs.slf4j.api)
    implementation(libs.jakarta.api)

    implementation(libs.casl.jp.lib)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.jupiter)
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.core)
}

tasks.test {
    useJUnitPlatform()
}