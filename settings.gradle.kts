// build-logic is listed here when convention plugins are activated (future phase):
// includeBuild("build-logic")

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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Amro"

// ─── Application module ───────────────────────────────────────────────────────
include(":app")

// ─── Core modules ─────────────────────────────────────────────────────────────
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:ui")

// ─── Feature modules ──────────────────────────────────────────────────────────
include(":feature:trending")
include(":feature:detail")
