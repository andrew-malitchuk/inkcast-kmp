plugins {
    id("dev.yamh.io.convention.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":data-core"))
        }
    }
}
