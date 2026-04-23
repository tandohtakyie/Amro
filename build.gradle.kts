// Top-level build file — declares plugins used across all modules.
// Each plugin is declared with `apply false` so sub-modules opt-in explicitly.
plugins {
    alias(libs.plugins.android.app)          apply false
    alias(libs.plugins.android.lib)          apply false
    alias(libs.plugins.kotlin.compose)       apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.di.hilt)              apply false
    alias(libs.plugins.google.ksp)           apply false
    alias(libs.plugins.db.room)              apply false
    alias(libs.plugins.tool.roborazzi)       apply false
    alias(libs.plugins.google.secrets)       apply false
}
