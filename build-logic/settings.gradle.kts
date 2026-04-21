// This build-logic project is activated by adding `includeBuild("build-logic")`
// to the root settings.gradle.kts (done in a future phase).

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
