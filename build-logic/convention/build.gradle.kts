import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "aim.high.amro.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

// Plugin artifacts used inside convention plugin source code
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("amroAndroidApplication") {
            id = "amro.android.application"
            implementationClass = "AmroAndroidApplicationConventionPlugin"
        }
        register("amroAndroidLibrary") {
            id = "amro.android.library"
            implementationClass = "AmroAndroidLibraryConventionPlugin"
        }
        register("amroAndroidFeature") {
            id = "amro.android.feature"
            implementationClass = "AmroAndroidFeatureConventionPlugin"
        }
        register("amroAndroidHilt") {
            id = "amro.android.hilt"
            implementationClass = "AmroAndroidHiltConventionPlugin"
        }
        register("amroAndroidRoom") {
            id = "amro.android.room"
            implementationClass = "AmroAndroidRoomConventionPlugin"
        }
    }
}
