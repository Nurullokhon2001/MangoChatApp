package mango.fzco.chat.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mango.fzco.chat.domain.use_case.SendAuthCodeUseCase

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideSendAuthCodeUseCase(repository: Repository): SendAuthCodeUseCase {
        return SendAuthCodeUseCase(repository)
    }
}