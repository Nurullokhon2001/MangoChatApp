package mango.fzco.chat.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mango.fzco.chat.domain.use_case.CheckAuthCodeUseCase
import mango.fzco.chat.domain.use_case.RegisterUserUseCase
import mango.fzco.chat.domain.use_case.SendAuthCodeUseCase

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideSendAuthCodeUseCase(repository: Repository): SendAuthCodeUseCase {
        return SendAuthCodeUseCase(repository)
    }

    @Provides
    fun provideCheckAuthCodeUseCase(repository: Repository): CheckAuthCodeUseCase {
        return CheckAuthCodeUseCase(repository)
    }

    @Provides
    fun provideRegisterUserUseCase(repository: Repository): RegisterUserUseCase {
        return RegisterUserUseCase(repository)
    }
}