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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

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
