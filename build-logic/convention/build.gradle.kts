import org.gradle.api.JavaVersion
//import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    `kotlin-dsl`
}

group = "com.trx.habitmeta.buildlogic"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
//kotlin.compilerOptions.jvmTarget = JvmTarget.JVM_11


dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.gson)
    implementation(libs.ktlint.gradlePlugin)

}

gradlePlugin {
    plugins {
        register("conventionPlugin") {
            id = "com.trx.habitmeta.buildlogic.convention"
            version = project.version
            implementationClass = "com.trx.habitmeta.buildlogic.plugin.ConventionPlugin"
        }
        register("applicationPlugin") {
            id = "com.trx.habitmeta.buildlogic.application"
            version = project.version
            implementationClass = "com.trx.habitmeta.buildlogic.plugin.ApplicationPlugin"
        }
    }
}