dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ktxml"))
    compileOnly("xmlpull:xmlpull:1.1.3.1")

    testImplementation(kotlin("test"))
    testImplementation(libs.assertjs.core)
    testImplementation("net.sf.kxml:kxml2:2.3.0")
}

tasks.test {
    useJUnit()
}
