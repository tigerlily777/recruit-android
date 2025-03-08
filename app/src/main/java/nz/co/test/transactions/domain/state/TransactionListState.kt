package nz.co.test.transactions.domain.state

import nz.co.test.transactions.data.model.Transaction

sealed class TransactionListState{
    data class Success(val transactionList: List<Transaction>) : TransactionListState()
    data class Error(val message: String) : TransactionListState()
    data object Loading : TransactionListState()
}
