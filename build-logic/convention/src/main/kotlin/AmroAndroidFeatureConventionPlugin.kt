import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Convention plugin for feature modules (feature:trending, feature:detail, …).
 * Composes: amro.android.library + amro.android.hilt + kotlin.compose
 * Adds: Compose build feature, :core:common and :core:ui as implicit dependencies.
 *
 * Feature modules only need to declare their own additional dependencies on top.
 */
class AmroAndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("amro.android.library")
            pluginManager.apply("amro.android.hilt")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                "implementation"(project(":core:common"))
                "implementation"(project(":core:ui"))
            }
        }
    }
}
