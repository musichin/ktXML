plugins {
    base
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.maven.publish) apply false
}

// kover {
//    useJacoco()
//    dependencies {
//        kover(project(":ktxml"))
//        kover(project(":ktxml-pull"))
//    }
// }

tasks.named<Delete>("clean") {
    delete(rootProject.layout.buildDirectory.get())
    delete(subprojects.map { it.layout.buildDirectory.get() })
}
