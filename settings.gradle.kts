pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") }
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
    versionCatalogs {
        create("deps") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Weather"
include(":app")
include(":app:app-domain")

include(":feature")
include(":feature:current")
include(":feature:forecast")
include(":feature:radar")
include(":feature:current:current-domain")
include(":feature:current:current-presentation")
include(":feature:forecast:forecast-domain")
include(":feature:forecast:forecast-presentation")
include(":feature:radar:radar-presentation")
include(":feature:radar:radar-domain")

include(":data")
include(":data:api")
include(":data:datastore")
include(":data:datastore:core")
include(":data:datastore:repo")

include(":common")
include(":common:entity")
include(":common:feature-api")
include(":common:base-ui")
include(":common:utils")
