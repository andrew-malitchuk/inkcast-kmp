plugins {
    id("dev.yamh.io.convention.feature")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.navigation3.ui)
            implementation(libs.kotlinx.serialization.core)

            implementation(projects.presentationFeatureAbout)
            implementation(projects.presentationFeatureConnection)
            implementation(projects.presentationFeatureHome)
            implementation(projects.presentationFeatureOnboarding)
            implementation(projects.presentationFeatureSettings)
            implementation(projects.presentationFeatureSplash)
            api(projects.presentationCoreNavigationApi)
        }
    }
}
