plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "jacoco")
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.13"
    }
    tasks.withType<Test> {
        finalizedBy(tasks.withType<JacocoReport>())
    }
    tasks.withType<JacocoReport> {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(true)
        }
        dependsOn(tasks.withType<Test>())
    }
}
