plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.domainUsecaseApi)
            implementation(projects.domainCore)
            implementation(projects.domainRepositoryApi)
            implementation(libs.koin.core)
        }
    }
}
