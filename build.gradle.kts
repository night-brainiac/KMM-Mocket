plugins {
    // KMM
    kotlin("multiplatform").version("1.7.10").apply(false)

    // Android
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)

    // Firebase
    id("com.google.gms.google-services").version("4.3.14").apply(false)
    id("com.google.firebase.crashlytics").version("2.9.2").apply(false)

    // Dokka
    id("org.jetbrains.dokka").version("1.7.20")

    // Linter
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
}

// Dokka - For documenting Gradle multi-module projects
tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(file("docs/")) // Custom output directory.
}

// KtLint
val ktLint: Configuration by configurations.creating

dependencies {
    ktLint("com.pinterest:ktlint:0.47.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val ktlintCheck by tasks.creating(JavaExec::class) { // Check Kotlin code style.
    classpath = ktLint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("--disabled_rules=package-name,no-wildcard-imports") // Used like command, here for disabling rules.
}

// Detekt
detekt {
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

// Base Tasks
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
