plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.db.room)
    alias(libs.plugins.di.hilt)
}

android {
    namespace = "aim.high.amro.core.database"
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.test.junit)
    testImplementation(libs.room.testing)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.coroutines)
}
