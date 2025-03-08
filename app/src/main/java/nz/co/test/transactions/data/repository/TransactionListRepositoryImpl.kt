package nz.co.test.transactions.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nz.co.test.transactions.data.model.Transaction
import nz.co.test.transactions.data.services.TransactionsService
import javax.inject.Inject

class TransactionListRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService
): TransactionListRepository {
    private var transactionList: List<Transaction> = emptyList()
    override suspend fun fetchTransactionList(): List<Transaction> {
        return withContext(Dispatchers.IO) {
            val response = transactionsService.retrieveTransactions()
            if (response.isSuccessful) {
                response.body()?.let {
                    transactionList = it
                    it
                } ?: emptyList()
            } else {
                response.errorBody()?.let {
                    throw Exception(it.string())
                }?: throw Exception("Unknown error")
                // Handle error in a better way so app won't crash
            }
        }
    }
}