plugins {
    id("dev.yamh.io.convention.feature")
    id("dev.yamh.io.convention.di")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.orbit.core)
            implementation(libs.orbit.viewmodel)
            implementation(libs.orbit.compose)

            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs.compose)

            implementation(libs.navigation3.ui)
            implementation(libs.kotlinx.serialization.core)

            implementation(projects.domainCore)
            implementation(projects.domainUsecaseApi)
            implementation(projects.presentationCoreLocalisation)
            implementation(projects.presentationCoreNavigationApi)
            implementation(projects.presentationCoreStyling)
            implementation(projects.presentationCoreUi)
        }
    }
}
