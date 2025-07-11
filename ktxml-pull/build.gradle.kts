plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kover)
    alias(libs.plugins.maven.publish)
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ktxml"))
    compileOnly("xmlpull:xmlpull:1.1.3.1")

    testImplementation(kotlin("test"))
    testImplementation(libs.assertjs.core)
    testImplementation("net.sf.kxml:kxml2:2.3.0")
}

kover {
    useJacoco()
}

tasks.test {
    useJUnit()
}
