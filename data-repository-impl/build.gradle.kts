plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":common-core"))
            implementation(project(":data-core"))
            implementation(project(":data-network-api"))
            implementation(project(":data-preference-api"))
            implementation(project(":domain-core"))
            implementation(project(":domain-repository-api"))
            implementation(libs.koin.core)
        }
    }
}
