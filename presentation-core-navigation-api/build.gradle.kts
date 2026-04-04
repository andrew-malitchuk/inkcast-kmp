plugins {
    id("dev.yamh.io.convention.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation3.ui)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}
