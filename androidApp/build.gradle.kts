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
        minSdk = 23 // WalletConnect minimum support 23
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
            excludes += "/META-INF/{AL2.0,LGPL2.1,INDEX.LIST,DEPENDENCIES,LICENSE.md,NOTICE.md}"
        }
    }
}

dependencies {

    // Modules
    implementation(project(":shared"))

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Compose UI
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.material3:material3") // Includes updated theming and components and Material You personalization features like dynamic color.
    implementation("androidx.compose.material3:material3-window-size-class") // window size utils
    implementation("androidx.compose.material:material-icons-extended") // Contains all Material icons
    implementation("androidx.activity:activity-compose:1.6.1") // Integration with Activity.

    // Compose UI - Android Studio Preview Support
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.2") // Provides the API required to declare @Preview composables.
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2") // Exposes information to our tools for better IDE support.

    // Adaptive
    implementation("com.google.accompanist:accompanist-adaptive:0.28.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.1.0")) // Import the BoM for the Firebase platform that you don't have to specify versions in Firebase library dependencies.
    implementation("com.google.firebase:firebase-crashlytics-ktx") // Get reports for crashes, non-fatal errors, and "Application Not Responding" (ANR) errors.
    implementation("com.google.firebase:firebase-analytics-ktx") // Collects usage and behavior data.
    implementation("com.google.firebase:firebase-perf-ktx") // Collect performance data.

    // Koin - A pragmatic lightweight dependency injection framework
    implementation("io.insert-koin:koin-android:3.3.1") // Android
    implementation("io.insert-koin:koin-androidx-compose:3.4.0") // Jetpack Compose
    implementation("io.insert-koin:koin-androidx-navigation:3.3.0") // Jetpack Navigation

    // Detection
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10") //  Provides memory leak detection.

    // Unit Test
    androidTestImplementation("androidx.test:runner:1.5.1")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.4")
}
