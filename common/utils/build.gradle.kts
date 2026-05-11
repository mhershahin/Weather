import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}

android {
    namespace = "com.service.utils"
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
    implementation(deps.google.material)

    //Test
    testImplementation(deps.junit)
    androidTestImplementation(deps.junit)
    androidTestImplementation(deps.androidx.test.espresso.core)

    // Coroutines
    implementation(deps.kotlinx.coroutines.core)
    implementation(deps.kotlinx.coroutines.android)

    //Retrofit
    implementation(deps.retrofit.core)

    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)

    //Compose
    implementation(deps.androidx.compose.ui)
    implementation(deps.androidx.compose.runtime)
    implementation(deps.androidx.compose.material)
    implementation(deps.androidx.compose.material3)
    implementation(deps.androidx.compose.material.icons.extended)
    implementation(deps.androidx.compose.ui.tooling.preview)
    implementation(deps.androidx.compose.animation)
    implementation(deps.androidx.activity.compose)
    implementation(deps.accompanist.permissions)

    implementation(projects.common.entity)
}