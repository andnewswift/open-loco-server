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
}

tasks.test {
    useJUnitPlatform()
}