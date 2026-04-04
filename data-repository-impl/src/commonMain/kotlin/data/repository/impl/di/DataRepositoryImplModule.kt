package data.repository.impl.di

import data.repository.impl.source.ConfigureRepositoryImpl
import data.repository.impl.source.ReaderRepositoryImpl
import domain.repository.api.source.repository.ConfigureRepository
import domain.repository.api.source.repository.ReaderRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin module that provides data-repository-impl bindings.
 *
 * Registers:
 * - [ConfigureRepositoryImpl] as the singleton implementation of [ConfigureRepository].
 * - [ReaderRepositoryImpl] as the singleton implementation of [ReaderRepository].
 */
public val dataRepositoryImplModule: Module = module {
    singleOf(::ConfigureRepositoryImpl) bind ConfigureRepository::class
    singleOf(::ReaderRepositoryImpl) bind ReaderRepository::class
}
