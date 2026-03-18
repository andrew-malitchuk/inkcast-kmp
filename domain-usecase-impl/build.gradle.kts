plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":domain-usecase-api"))
            implementation(project(":domain-core"))
            implementation(project(":domain-repository-api"))
            implementation(libs.koin.core)
        }
    }
}
