plugins {
    id("java")
}

group = "hk.ust.comp3021.lab"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    val junitVersion = "5.10.0"
    val junitPlatformVersion = "1.10.0"

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-commons:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:$junitPlatformVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}
