plugins {
    id("dev.yamh.io.convention.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":presentation-core-navigation-api"))
            implementation(libs.navigation3.ui)
            implementation(libs.kotlinx.serialization.core)

            implementation(project(":presentation-feature-about"))
            implementation(project(":presentation-feature-connection"))
            implementation(project(":presentation-feature-home"))
            implementation(project(":presentation-feature-onboarding"))
            implementation(project(":presentation-feature-settings"))
            implementation(project(":presentation-feature-splash"))
        }
    }
}
