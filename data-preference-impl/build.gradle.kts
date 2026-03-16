plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":data-preference-api"))
            implementation(project(":data-core"))
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.datastore.preferences)
        }
    }
}
