plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.di.hilt)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "aim.high.amro.core.network"
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
    implementation(projects.core.common)
    implementation(projects.core.model)

    // Networking Bundle
    implementation(libs.bundles.bundle.network)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.coroutines)
}
