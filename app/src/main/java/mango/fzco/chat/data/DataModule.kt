package mango.fzco.chat.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mango.fzco.chat.domain.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideChatApi(okHttpClient: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl("https://plannerok.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    fun provideRepository(chatApi: ChatApi): Repository {
        return RepositoryImpl(chatApi)
    }
}