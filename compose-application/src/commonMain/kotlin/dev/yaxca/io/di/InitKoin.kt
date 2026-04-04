package dev.yaxca.io.di

import data.network.impl.di.dataNetworkImplModule
import data.preference.impl.di.dataPreferenceImplModule
import data.repository.impl.di.dataRepositoryImplModule
import domain.usecase.impl.di.domainUseCaseImplModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import presentation.feature.about.di.presentationFeatureAboutModule
import presentation.feature.connection.di.presentationFeatureConnectionModule
import presentation.feature.home.create.di.presentationFeatureHomeCreateModule
import presentation.feature.home.device.di.presentationFeatureHomeDeviceModule
import presentation.feature.home.di.presentationFeatureHomeModule
import presentation.feature.home.files.di.presentationFeatureHomeFilesModule
import presentation.feature.home.sleep.di.presentationFeatureHomeSleepModule
import presentation.feature.onboarding.di.presentationFeatureOnboardingModule
import presentation.feature.settings.di.presentationFeatureSettingsModule
import presentation.feature.splash.di.presentationFeatureSplashModule

/**
 * Initializes the Koin dependency injection graph with all application modules.
 *
 * Registers data, domain, and presentation modules in the correct order.
 * Called once during application startup from platform-specific entry points.
 *
 * @param config Optional platform-specific Koin configuration (e.g., Android context).
 *
 * @see doInitKoin
 */
public fun initKoin(config: KoinAppDeclaration = {}) {
    startKoin {
        config()
        modules(
            dataNetworkImplModule,
            dataPreferenceImplModule,
            dataRepositoryImplModule,
            domainUseCaseImplModule,
            presentationFeatureAboutModule,
            presentationFeatureConnectionModule,
            presentationFeatureHomeModule,
            presentationFeatureHomeCreateModule,
            presentationFeatureHomeDeviceModule,
            presentationFeatureHomeFilesModule,
            presentationFeatureHomeSleepModule,
            presentationFeatureOnboardingModule,
            presentationFeatureSettingsModule,
            presentationFeatureSplashModule,
        )
    }
}
