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

            implementation(project(":domain-core"))
            implementation(project(":domain-usecase-api"))
            implementation(project(":presentation-core-localisation"))
            implementation(project(":presentation-core-navigation-api"))
            implementation(project(":presentation-core-styling"))
            implementation(project(":presentation-core-ui"))

            implementation(project(":presentation-feature-home-create"))
            implementation(project(":presentation-feature-home-device"))
            implementation(project(":presentation-feature-home-files"))
            implementation(project(":presentation-feature-home-sleep"))
        }
    }
}
