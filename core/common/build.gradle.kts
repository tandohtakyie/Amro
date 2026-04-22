plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.di.hilt)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "aim.high.amro.core.common"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
