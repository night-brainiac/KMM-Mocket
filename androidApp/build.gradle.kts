plugins {
    kotlin("android")
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.dokka")
}

android {
    namespace = "me.night_brainiac.mocket.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "me.night_brainiac.mocket.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1" // Same version as compose dependencies
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Modules
    implementation(project(":shared"))

    // Compose UI
    implementation("androidx.compose.ui:ui:1.3.1") // This library contains the primitives that form the Compose UI Toolkit.
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1") // This library provides the API required to declare @Preview composables.
    implementation("androidx.compose.material:material:1.3.1") // TODO upgrade to Material3
    implementation("androidx.compose.foundation:foundation:1.3.1") // Providing the high-level building blocks for both application and design-system developers.
    implementation("androidx.activity:activity-compose:1.6.1") // Compose integration with Activity.

    debugImplementation("androidx.compose.ui:ui-tooling:1.3.1") // This library exposes information to our tools for better IDE support.

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.1.0")) // Import the BoM for the Firebase platform that you don't have to specify versions in Firebase library dependencies.
    implementation("com.google.firebase:firebase-crashlytics-ktx") // Get reports for crashes, non-fatal errors, and "Application Not Responding" (ANR) errors.
    implementation("com.google.firebase:firebase-analytics-ktx") // Collects usage and behavior data.
    implementation("com.google.firebase:firebase-perf-ktx") // Collect performance data.

    // Detection
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10") //  A memory leak detection library for Android.

    // Unit Test
    androidTestImplementation("androidx.test:runner:1.5.1")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.4")
}
