dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation(libs.assertjs.core)
}

tasks.test {
    useJUnit()
}
