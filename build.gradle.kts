import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "sms_service"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	/**
	 * Spring boot starters
	 */
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework:spring-jdbc")
	implementation("org.springframework.kafka:spring-kafka:3.0.11")

	/**
	 * Test containers
	 */
	implementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("com.redis.testcontainers:testcontainers-redis-junit-jupiter:1.4.6")
	testImplementation("org.testcontainers:kafka:1.19.1")

	/**
	 * Kotlin
	 */
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	/**
	 * Utils
	 */
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	compileOnly("org.projectlombok:lombok:1.18.30")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.mapstruct:mapstruct-processor:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	/**
	 * Tests
	 */
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.assertj:assertj-core:3.24.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	/**
	 * Swagger
	 */
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.2.0")

	/**
	 *  Google SMTP
	 */
	implementation("org.springframework.boot:spring-boot-starter-mail")

	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	implementation("se.akerfeldt:okhttp-signpost:1.1.0")
	implementation("org.json:json:20160810")
	compileOnly("com.squareup.okhttp3:okhttp:3.4.1")
	compileOnly("se.akerfeldt:okhttp-signpost:1.1.0")
	compileOnly("org.json:json:20160810")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	archiveFileName.set("application.jar")
}
