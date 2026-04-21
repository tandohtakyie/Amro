plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "aim.high.amro.core.model"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        consumerProguardFiles(rootProject.file("consumer-rules.pro"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
