plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.dokka")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Koin
                implementation("io.insert-koin:koin-core:3.2.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

                // Koin
                implementation("io.insert-koin:koin-test:3.2.2")
            }
        }

        val androidMain by getting {
            dependencies {
                // WalletConnect
                implementation("com.walletconnect:android-core:1.5.0") // WalletConnect Core SDK.
                implementation("com.walletconnect:sign:2.3.0") // WalletConnect v2 Sign protocol.

                // Log
                api("com.jakewharton.timber:timber:5.0.1") // A logger with a small, extensible API which provides utility on top of Android's normal Log class.
            }
        }
        val androidTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "me.night_brainiac.mocket"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}
