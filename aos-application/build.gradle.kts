import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.targetSdk.get().toInt()

    // Load signing configuration from local properties
    val signingPropertiesFile = File(rootDir, "./configure/secrets/signing.properties")
    if (signingPropertiesFile.exists()) {
        val signingProperties = Properties().also {
            it.load(FileInputStream(signingPropertiesFile))
        }

        signingConfigs {
            getByName("debug") {
                keyAlias = signingProperties.getProperty("debugKey")
                keyPassword = signingProperties.getProperty("debugPassword")
                storePassword = signingProperties.getProperty("debugPassword")
                storeFile = File(rootDir, "./configure/signing/yaxca.debug")
            }
            create("release") {
                keyAlias = signingProperties.getProperty("releaseKey")
                keyPassword = signingProperties.getProperty("releasePassword")
                storePassword = signingProperties.getProperty("releasePassword")
                storeFile = File(rootDir, "./configure/signing/yaxca.release")
            }
        }
    }

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.findByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                rootProject.file("proguard-rules.pro"),
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    target {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    dependencies {
        implementation(projects.composeApplication)
        implementation(projects.dataNetworkImpl)
        implementation(libs.androidx.activity.compose)
        implementation(libs.koin.android)
    }
}
