package nz.co.test.transactions.domain.state

import nz.co.test.transactions.data.model.FormattedTransaction

sealed class TransactionListState{
    data class Success(val formattedTransactionList: List<FormattedTransaction>) : TransactionListState()
    data class Error(val message: String) : TransactionListState()
    data object Loading : TransactionListState()
}
