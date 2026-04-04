plugins {
    id("dev.yamh.io.convention.feature")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation("androidx.exifinterface:exifinterface:1.4.0")
        }
    }
}
