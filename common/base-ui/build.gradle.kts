import org.jetbrains.kotlin.gradle.dsl.JvmTarget
plugins {
    alias(deps.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}

android {
    namespace = "com.service.base_ui"
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

    implementation(deps.androidx.core.ktx)
    implementation(deps.androidx.appcompat)
    implementation(deps.google.material)


    //Compose
    implementation(deps.androidx.compose.ui)
    implementation(deps.androidx.compose.runtime)
    implementation(deps.androidx.compose.material)
    implementation(deps.androidx.compose.material.icons.extended)
    implementation(deps.androidx.compose.ui.tooling.preview)
    implementation(deps.androidx.compose.animation)
    implementation(deps.androidx.activity.compose)
    implementation(deps.accompanist.permissions)

    //Pager
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.pager)


    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)

    //Lifecycle
    implementation(deps.androidx.lifecycle.runtime)
    implementation(deps.androidx.lifecycle.viewmodel.compose)

    implementation(deps.kotlinx.collections.immutable)


    //Common
    implementation(projects.common.entity)
    implementation(projects.common.utils)
}