plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.di.hilt)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "aim.high.amro.core.domain"
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
    implementation(projects.core.data)

    // Store5 (Required for Use Case return types)
    implementation(libs.caching.store5)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
 
    testImplementation(libs.bundles.bundle.testing.unit)
}
