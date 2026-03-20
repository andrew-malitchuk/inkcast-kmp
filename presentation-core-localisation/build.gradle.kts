plugins {
    id("dev.yamh.io.convention.feature")
}

compose.resources {
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
    }
}
