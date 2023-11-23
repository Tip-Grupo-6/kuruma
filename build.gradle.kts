import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.tip"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	//security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

	implementation("org.wso2.orbit.io.projectreactor:reactor-core:3.3.9.wso2v1")

	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")

	// push notifications
	implementation("nl.martijndwars:web-push:5.1.1")
	implementation("org.bouncycastle:bcprov-jdk15on:1.70")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2")
	testImplementation("io.quarkus:quarkus-junit5-mockito:2.12.2.Final")
	testImplementation("io.mockk:mockk:1.12.4")
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