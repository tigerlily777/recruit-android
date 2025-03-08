package nz.co.test.transactions.data.repository

import nz.co.test.transactions.data.model.Transaction

interface TransactionListRepository {
    suspend fun fetchTransactionList(): List<Transaction>
}