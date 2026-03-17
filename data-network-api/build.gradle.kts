plugins {
    id("dev.yamh.io.convention.library")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":data-core"))
            implementation(libs.ktor.client.core)
            // NOTE: api scope because JsonElement is exposed in SettingItemNetwork's public API.
            api(libs.kotlinx.serialization.json)
        }
    }
}
