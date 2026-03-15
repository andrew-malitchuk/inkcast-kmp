rootProject.name = "inkcast-kmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":compose-application")
include(":androidApp")

// common
include(":common-core")

// data
include(":data-core")
include(":data-network-api")
include(":data-network-impl")
include(":data-preference-api")
include(":data-preference-impl")
include(":data-repository-impl")

// domain
include(":domain-core")
include(":domain-repository-api")
include(":domain-usecase-api")
include(":domain-usecase-impl")

// presentation-core
include(":presentation-core-localisation")
include(":presentation-core-navigation-api")
include(":presentation-core-navigation-impl")
include(":presentation-core-styling")
include(":presentation-core-ui")
include(":presentation-core-platform")

// presentation-feature
include(":presentation-feature-connection")
include(":presentation-feature-about")
include(":presentation-feature-home")
include(":presentation-feature-home-create")
include(":presentation-feature-home-device")
include(":presentation-feature-home-files")
include(":presentation-feature-home-sleep")
include(":presentation-feature-onboarding")
include(":presentation-feature-settings")
include(":presentation-feature-splash")