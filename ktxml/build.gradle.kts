plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.easytesting:fest-assert-core:2.0M10")
}

tasks.test {
    useJUnit()
}
