import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}
android {
    namespace = "com.service.api"
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(deps.androidx.core.ktx)
    // Coroutines
    implementation(deps.kotlinx.coroutines.core)
    implementation(deps.kotlinx.coroutines.android)
    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)

    //Retrofit
    implementation(deps.retrofit.core)
    implementation(deps.retrofit.converter.scalars)
    implementation(deps.okhttp.logging)
    implementation(deps.retrofit.kotlinx.serialization)

    implementation(projects.common.entity)
    implementation(projects.common.utils)

    // Kotlin Serialization
    implementation(deps.kotlinx.serialization.json)
}