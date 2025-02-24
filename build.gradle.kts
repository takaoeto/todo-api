plugins {
    java
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "6.4.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.1")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("buildApiDoc") {
    generatorName.set("html2")
    inputSpec.set("$rootDir/src/main/resources/api-schema.yaml")
    outputDir.set("$buildDir/apidoc")
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("buildSpringServer") {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/api-schema.yaml")
    outputDir.set("$buildDir/spring")
    apiPackage.set("com.example.todoapi.controller")
    modelPackage.set("com.example.todoapi.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true"
        )
    )
}

tasks.named("compileJava") {
    dependsOn("buildSpringServer")
}

// Gradle 8+ に対応
sourceSets["main"].java.srcDirs("$buildDir/spring/src/main/java")