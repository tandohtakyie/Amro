plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.di.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "aim.high.amro.core.data"
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
    implementation(projects.core.network)
    implementation(projects.core.database)

    // Store5
    implementation(libs.caching.store5)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Serialization
    implementation(libs.json.serialization)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.androidx.junit)
    testImplementation(libs.test.espresso.core)
    testImplementation(libs.test.androidx.arch.core)
}
