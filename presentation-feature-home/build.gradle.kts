plugins {
    id("dev.yamh.io.convention.feature")
    id("dev.yamh.io.convention.di")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.orbit.core)
            implementation(libs.orbit.viewmodel)
            implementation(libs.orbit.compose)

            implementation(projects.domainCore)
            implementation(projects.domainUsecaseApi)
            implementation(projects.presentationCoreLocalisation)
            implementation(projects.presentationCoreNavigationApi)
            implementation(projects.presentationCoreStyling)
            implementation(projects.presentationCoreUi)

            implementation(projects.presentationFeatureHomeCreate)
            implementation(projects.presentationFeatureHomeDevice)
            implementation(projects.presentationFeatureHomeFiles)
            implementation(projects.presentationFeatureHomeSleep)
        }
    }
}
