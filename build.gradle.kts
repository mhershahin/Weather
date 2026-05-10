buildscript {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap") }
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
    dependencies {
        classpath(deps.r8.tools)
        classpath(deps.android.gradlePlugin)
        classpath(deps.google.services)
        classpath(deps.grgit.gradle)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap") }
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
}