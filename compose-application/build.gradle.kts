import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("dev.yamh.io.convention.application")
    id("dev.yamh.io.convention.di")
}

kotlin {
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.domainCore)
            implementation(projects.dataNetworkImpl)
            implementation(projects.dataPreferenceImpl)
            implementation(projects.dataRepositoryImpl)
            implementation(projects.domainUsecaseImpl)
            implementation(projects.presentationCoreLocalisation)
            implementation(projects.presentationCoreNavigationApi)
            implementation(projects.presentationCoreNavigationImpl)
            implementation(projects.presentationCoreStyling)
            implementation(projects.presentationCoreUi)
            implementation(projects.presentationFeatureAbout)
            implementation(projects.presentationFeatureConnection)
            implementation(projects.presentationFeatureHome)
            implementation(projects.presentationFeatureHomeCreate)
            implementation(projects.presentationFeatureHomeDevice)
            implementation(projects.presentationFeatureHomeFiles)
            implementation(projects.presentationFeatureHomeSleep)
            implementation(projects.presentationFeatureOnboarding)
            implementation(projects.presentationFeatureSettings)
            implementation(projects.presentationFeatureSplash)
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
