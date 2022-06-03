import com.vanniktech.maven.publish.MavenPublishPluginExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.20.0")
    }
}

subprojects {
    repositories {
        mavenCentral()
    }

    plugins.withId("com.vanniktech.maven.publish") {
        configure<MavenPublishPluginExtension> {
            sonatypeHost = SonatypeHost.S01
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    apply(plugin = "jacoco")
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.7"
    }
    tasks.withType<Test> {
        finalizedBy(tasks.withType<JacocoReport>())
    }
    tasks.withType<JacocoReport> {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.required.set(false)
        }
        dependsOn(tasks.withType<Test>())
    }
}
