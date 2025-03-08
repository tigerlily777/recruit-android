package nz.co.test.transactions.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.data.model.Transaction
import nz.co.test.transactions.data.model.formatTransactionListItem
import nz.co.test.transactions.data.repository.TransactionListRepository
import nz.co.test.transactions.domain.state.TransactionListState
import javax.inject.Inject

class TransactionListUseCaseImpl @Inject constructor(
    private val transactionListRepository: TransactionListRepository
): TransactionListUseCase {
    override suspend fun fetchTransactionList(): Flow<TransactionListState> = flow {
        emit(TransactionListState.Loading)
        try {
            val transactions = transactionListRepository.fetchTransactionList()
            emit(TransactionListState.Success(convertTransactionList(transactions)))
        } catch (e: Exception) {
            emit(TransactionListState.Error(e.message ?: "An error occurred"))
        }
    }

    private fun convertTransactionList(transactions: List<Transaction>): List<FormattedTransaction> {
        return transactions.map { formatTransactionListItem(it) }
    }
}