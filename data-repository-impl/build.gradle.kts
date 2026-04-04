plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.commonCore)
            implementation(projects.dataCore)
            implementation(projects.dataNetworkApi)
            implementation(projects.dataPreferenceApi)
            implementation(projects.domainCore)
            implementation(projects.domainRepositoryApi)
            implementation(libs.koin.core)
        }
    }
}
