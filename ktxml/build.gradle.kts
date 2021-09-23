plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.easytesting:fest-assert-core:2.0M10")
}

tasks.test {
    useJUnit()
}
