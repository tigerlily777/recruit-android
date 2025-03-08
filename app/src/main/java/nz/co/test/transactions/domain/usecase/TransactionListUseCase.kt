package nz.co.test.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.domain.state.TransactionListState

interface TransactionListUseCase {
    suspend fun fetchTransactionList(): Flow<TransactionListState>
}