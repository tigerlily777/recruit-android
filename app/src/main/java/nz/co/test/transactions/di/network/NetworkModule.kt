package nz.co.test.transactions.di.network

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.data.repository.TransactionListRepository
import nz.co.test.transactions.data.repository.TransactionListRepositoryImpl
import nz.co.test.transactions.data.services.TransactionsService
import nz.co.test.transactions.domain.usecase.TransactionListUseCase
import nz.co.test.transactions.domain.usecase.TransactionListUseCaseImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesTransactionService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TransactionBindings {

        @Singleton
        @Binds
        abstract fun bindTransactionUseCase(impl: TransactionListUseCaseImpl): TransactionListUseCase

        @Singleton
        @Binds
        abstract fun bindTransactionRepository(impl: TransactionListRepositoryImpl): TransactionListRepository
    }
}