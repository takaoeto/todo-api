plugins {
    java
    id ("org.springframework.boot") version "3.0.4"
	id ("io.spring.dependency-management") version "1.1.0"
	id ("org.openapi.generator") version "6.4.0"
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
    compileOnly("io.swagger.core.v3:swagger-annotations:2.2.8") // Swaggerアノテーション
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    implementation("org.openapitools:jackson-databind-nullable:0.2.2")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2") // Javax Annotation
    implementation("javax.validation:validation-api:2.0.1.Final") // Javax Validation
    implementation("javax.servlet:javax.servlet-api:4.0.1") // Javax Servlet (必要な場合)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("buildApiDoc") {
    generatorName.set("html2") // ドキュメントの形式を指定
    inputSpec.set("$rootDir/src/main/resources/api-schema.yaml") // APIスキーマのパス
    outputDir.set("$buildDir/apidoc") // 出力先フォルダ
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("buildSpringServer") {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/api-schema.yaml")
    outputDir.set("$buildDir/spring")
    apiPackage.set("com.example.todoapi.controller")
    modelPackage.set("com.example.todoapi.model")
    configOptions.set(mapOf(
        "interfaceOnly" to "true"
    ))
}

tasks.named("compileJava") {
    dependsOn("buildSpringServer")
}

openApiValidate {
	inputSpec.set("$rootDir/src/main/resources/api-schema.yaml")
}

sourceSets["main"].java.srcDir("$buildDir/spring/src/main/java")

