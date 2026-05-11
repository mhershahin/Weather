import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
    alias(deps.plugins.kotlin.serialization)
}

android {
    namespace = "com.service.repo"
    compileSdk {
        version = release(deps.versions.sdk.compile.get().toInt()) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = deps.versions.sdk.min.get().toInt()
        testInstrumentationRunner = deps.versions.test.instrumentation.runner.get().toString()
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        val jvmVersion = deps.versions.jvm.get()
        sourceCompatibility = JavaVersion.toVersion(jvmVersion)
        targetCompatibility = JavaVersion.toVersion(jvmVersion)
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(deps.versions.jvm.get()))
        }
    }
}

dependencies {
    implementation(deps.androidx.core.ktx)
    implementation(deps.androidx.appcompat)

    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)

    // Coroutines
    implementation(deps.kotlinx.coroutines.core)

    // Kotlin Serialization
    implementation(deps.kotlinx.serialization.json)

    implementation(projects.common.entity)
    implementation(projects.common.utils)
    implementation(projects.data.api)
    implementation(projects.data.datastore.core)

    testImplementation(deps.junit)
}
