import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(deps.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}

android {
    namespace = "com.service.splash_presentation"
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
        compose = true
    }
}

dependencies {
    //Compose
    implementation(deps.androidx.compose.ui)
    implementation(deps.androidx.compose.runtime)
    implementation(deps.androidx.compose.material)
    implementation(deps.androidx.compose.ui.tooling.preview)
    debugImplementation(deps.androidx.compose.ui.tooling)
    implementation(deps.androidx.navigation.compose)
    implementation(deps.accompanist.permissions)

    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)

    //Lifecycle
    implementation(deps.androidx.lifecycle.viewmodel.compose)

    //Coroutines
    implementation(deps.kotlinx.coroutines.core)

    implementation(projects.common.entity)
    implementation(projects.common.utils)
    implementation(projects.common.baseUi)
    implementation(projects.common.featureApi)
    implementation(projects.feature.splash.splashDomain)
}