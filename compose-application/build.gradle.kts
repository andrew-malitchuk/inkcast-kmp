import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("dev.yamh.io.convention.application")
    id("dev.yamh.io.convention.di")
}

kotlin {
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(project(":domain-core"))
            implementation(project(":data-network-impl"))
            implementation(project(":data-preference-impl"))
            implementation(project(":data-repository-impl"))
            implementation(project(":domain-usecase-impl"))
            implementation(project(":presentation-core-localisation"))
            implementation(project(":presentation-core-navigation-api"))
            implementation(project(":presentation-core-navigation-impl"))
            implementation(project(":presentation-core-styling"))
            implementation(project(":presentation-core-ui"))
            implementation(project(":presentation-feature-about"))
            implementation(project(":presentation-feature-connection"))
            implementation(project(":presentation-feature-home"))
            implementation(project(":presentation-feature-home-create"))
            implementation(project(":presentation-feature-home-device"))
            implementation(project(":presentation-feature-home-files"))
            implementation(project(":presentation-feature-home-sleep"))
            implementation(project(":presentation-feature-onboarding"))
            implementation(project(":presentation-feature-settings"))
            implementation(project(":presentation-feature-splash"))
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "dev.yaxca.io.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.yaxca.io"
            packageVersion = "1.0.0"
        }
    }
}
