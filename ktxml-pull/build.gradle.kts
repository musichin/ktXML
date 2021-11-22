plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(project(":ktxml"))
    compileOnly("xmlpull:xmlpull:1.1.3.1")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:4.1.0")
    testImplementation("org.easytesting:fest-assert-core:2.0M10")
    testImplementation("net.sf.kxml:kxml2:2.3.0")
}

tasks.test {
    useJUnit()
}
