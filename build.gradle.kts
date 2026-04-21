// Top-level build file — declares plugins used across all modules.
// Each plugin is declared with `apply false` so sub-modules opt-in explicitly.
plugins {
    alias(libs.plugins.android.application)  apply false
    alias(libs.plugins.android.library)      apply false
    alias(libs.plugins.kotlin.jvm)           apply false
    alias(libs.plugins.kotlin.compose)       apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt)                 apply false
    alias(libs.plugins.ksp)                  apply false
    alias(libs.plugins.room)                 apply false
    alias(libs.plugins.roborazzi)            apply false
}
