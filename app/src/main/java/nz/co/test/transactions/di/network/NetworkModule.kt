package nz.co.test.transactions.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.data.services.TransactionsService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesTransactionService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)
}