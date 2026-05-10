import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
}

composeCompiler {
    includeSourceInformation = false
}
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.get()))
    }
}
android {
    compileSdk = libs.versions.sdk.compile.get().toInt()
    namespace = "com.service.weather"
    defaultConfig {
        applicationId = "com.service.weather"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = libs.versions.app.code.get().toInt()
        versionName = libs.versions.app.name.get()
        testInstrumentationRunner = libs.versions.test.instrumentation.runner.get().toString()
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources{
            merges.add("build-data.properties")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        val jvmVersion = libs.versions.jvm.get()
        sourceCompatibility = JavaVersion.toVersion(jvmVersion)
        targetCompatibility = JavaVersion.toVersion(jvmVersion)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    implementation(deps.androidx.core.ktx)
    implementation(deps.androidx.activity.compose)

    //Compose
    implementation(deps.androidx.compose.ui)
    implementation(deps.androidx.compose.runtime)
    implementation(deps.androidx.compose.material)
    implementation(deps.androidx.compose.ui.tooling.preview)
    implementation(deps.androidx.navigation.compose)
    implementation(deps.androidx.lifecycle.extensions)

    //Accompanist
    implementation(deps.accompanist.insets)

    //Compose Test
    debugImplementation(deps.androidx.compose.ui.test)
    debugImplementation(deps.androidx.compose.ui.tooling)

    //Hilt
    //Hilt
    implementation(deps.hilt.android)
    ksp(deps.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Lifecycle
    implementation(deps.androidx.lifecycle.runtime)
    implementation(deps.androidx.lifecycle.viewmodel.compose)

    //Test
    testImplementation(deps.junit)
    androidTestImplementation(deps.junit)

// Leak Canary
//        debugImplementation(deps.leakcanary.android)

    // Coroutines
    implementation(deps.kotlinx.coroutines.core)
    implementation(deps.kotlinx.coroutines.android)

    //Jackson
    implementation(deps.jackson.core)
    implementation(deps.jackson.kotlin)
    implementation(deps.jackson.databind)

    implementation(deps.retrofit.converter.jackson)
    implementation(deps.okhttp.logging)



    //Desugaring
    coreLibraryDesugaring(deps.desugar.jdk.libs)

    implementation(deps.kotlinx.collections.immutable)

// App & Common
    implementation(projects.app.appDomain)
    implementation(projects.common.entity)
    implementation(projects.common.baseUi)
    implementation(projects.common.utils)
    implementation(projects.common.featureApi)

    // Feature Presentations
    implementation(projects.feature.current.currentPresentation)
    implementation(projects.feature.forecast.forecastPresentation)
    implementation(projects.feature.radar.radarPresentation)


    // Data & Services
    implementation(projects.data.api)
    implementation(projects.data.datastore.repo)
}