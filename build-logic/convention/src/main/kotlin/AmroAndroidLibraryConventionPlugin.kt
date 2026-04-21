import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for all Android library modules (core:*, feature:*).
 * Applies: com.android.library
 * Note: kotlin.android is not needed since AGP 9.0 includes Kotlin support.
 * Configures: compileSdk, minSdk, Java 17.
 */
class AmroAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.consumerProguardFiles(rootProject.file("consumer-rules.pro"))
            }
        }
    }
}
