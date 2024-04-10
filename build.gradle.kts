group = "io.github.selevinia"
version = "0.5.0"
description = "Spring boot starter for Reactive Spring Data module for Tarantool"

plugins {
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenLocal()
    mavenCentral()
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api("org.springframework.boot:spring-boot-starter:3.2.4")
    api("io.github.selevinia:selevinia-spring-boot-autoconfigure-tarantool:0.5.0")
    api("io.github.selevinia:spring-data-tarantool:0.5.0")
    api("io.tarantool:cartridge-driver:0.13.0")
    api("io.projectreactor:reactor-core:3.6.4")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("Reactive Spring Boot Data Tarantool Starter")
                description.set("Spring boot starter for Reactive Spring Data module for Tarantool")
                url.set("https://github.com/selevinia/spring-boot-starter-data-tarantool-reactive")

                scm {
                    connection.set("scm:git:git://github.com/selevinia/spring-boot-starter-data-tarantool-reactive.git")
                    developerConnection.set("scm:git:ssh://github.com/selevinia/spring-boot-starter-data-tarantool-reactive.git")
                    url.set("https://github.com/selevinia/spring-boot-starter-data-tarantool-reactive")
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("rx-alex")
                        name.set("Alexander Rublev")
                        email.set("invalidator.post@gmail.com")
                    }
                    developer {
                        id.set("t-obscurity")
                        name.set("Tatiana Blinova")
                        email.set("blinova.tv@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = rootProject.findProperty("nexus.username").toString()
                password = rootProject.findProperty("nexus.password").toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}