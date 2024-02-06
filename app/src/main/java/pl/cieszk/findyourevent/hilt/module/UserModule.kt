package pl.cieszk.findyourevent.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.cieszk.findyourevent.data.repositories.impl.UserRepositoryImpl
import pl.cieszk.findyourevent.data.repositories.module.UserRepository
import pl.cieszk.findyourevent.data.service.module.StorageService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository {
        return userRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideStorageService(

    )
}