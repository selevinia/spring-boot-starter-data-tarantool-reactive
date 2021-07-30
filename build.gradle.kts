group = "io.github.selevinia"
version = "0.1.0"
description = "Spring boot starter for Reactive Spring Data module for Tarantool"

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("org.springframework.boot:spring-boot-starter:2.5.2")
    api("io.github.selevinia:spring-data-tarantool:0.1.0")
    api("io.tarantool:cartridge-driver:0.4.3")
    api("io.projectreactor:reactor-core:3.4.7")

    implementation("org.springframework.data:spring-data-commons:2.4.10")

    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor:2.5.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}