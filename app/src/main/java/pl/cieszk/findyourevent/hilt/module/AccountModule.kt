package pl.cieszk.findyourevent.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.cieszk.findyourevent.data.repositories.module.UserRepository
import pl.cieszk.findyourevent.data.service.impl.AccountServiceImpl
import pl.cieszk.findyourevent.data.service.module.AccountService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    @Singleton
    fun provideAccountService(userRepository: UserRepository): AccountService {
        return AccountServiceImpl(userRepository)
    }
}