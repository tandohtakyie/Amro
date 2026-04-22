// build-logic is listed here when convention plugins are activated (future phase):
// includeBuild("build-logic")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.dagger.hilt.android") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "com.google.android.libraries.mapsplatform.secrets-gradle-plugin") {
                useModule("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${requested.version}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Amro"

//  Application module
include(":app")

// Core modules
include(":core:common")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:designsystem")

// Feature modules
include(":feature:trending")
include(":feature:detail")
