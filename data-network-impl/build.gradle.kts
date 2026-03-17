plugins {
    id("dev.yamh.io.convention.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    // NOTE: Suppress beta warning for expect/actual object (NetworkProvider).
    // Tracked: https://youtrack.jetbrains.com/issue/KT-61573
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
    sourceSets {
        commonMain.dependencies {
            api(project(":data-network-api"))
            implementation(project(":data-core"))
            implementation(project(":data-preference-api"))
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.websockets)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
    }
}
