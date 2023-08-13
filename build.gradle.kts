plugins {
    id("java")
}

group = "org.openloco"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.netty:netty-all:4.1.96.Final")
    implementation("org.mongodb:bson:4.10.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.7.1")
}

tasks.test {
    useJUnitPlatform()
}