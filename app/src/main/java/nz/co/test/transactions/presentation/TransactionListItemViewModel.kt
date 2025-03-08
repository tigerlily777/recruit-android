package nz.co.test.transactions.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.data.model.formatTransactionListItem
import nz.co.test.transactions.data.repository.TransactionListRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class TransactionListItemViewModel @Inject constructor(
    private val transactionListRepository: TransactionListRepository
): ViewModel() {
    fun getFormattedTransactionItem(transactionId: Int): FormattedTransaction? =
        transactionListRepository.getTransactionListItemById(transactionId)
            ?.let { formatTransactionListItem(it) }

    fun calculateGst(decimal: BigDecimal): BigDecimal =
        decimal.times(0.15.toBigDecimal()).setScale(2, RoundingMode.HALF_UP)
}