import org.jetbrains.kotlin.gradle.dsl.JvmTarget
plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.ksp)
    alias(deps.plugins.hilt)
}
android {
    namespace = "com.service.feature_api"
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
    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)
    //Common
    implementation(deps.androidx.navigation.compose)
    implementation(projects.common.entity)
}