import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for the :app module.
 * Applies: com.android.application + kotlin.compose
 * Note: kotlin.android is not needed since AGP 9.0 includes Kotlin support.
 * Configures: compileSdk, minSdk, targetSdk, Java 17, Compose.
 */
class AmroAndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}
