package nz.co.test.transactions.presentation

import io.mockk.coEvery
import io.mockk.mockk
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.data.model.Transaction
import nz.co.test.transactions.data.repository.TransactionListRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

class TransactionListItemViewModelTest {

    private lateinit var viewModel: TransactionListItemViewModel
    private lateinit var repository: TransactionListRepository

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = TransactionListItemViewModel(repository)
    }

    @Test
    fun `getFormattedTransactionItem returns formatted transaction`() {
        val transactionId = 1
        val transaction = Transaction(
            id = transactionId,
            transactionDate = "2021-01-01T01:00:00",
            summary = "Test",
            debit = "100.0",
            credit = "0.0"
        )
        val formattedTransaction = FormattedTransaction(
            id = transactionId,
            transactionDate = LocalDate.parse("2021-01-01"),
            transactionTime = LocalTime.parse("14:00:00"),
            summary = "Test",
            debit = BigDecimal("100.0"),
            credit = BigDecimal("0.0")
        )

        coEvery { repository.getTransactionListItemById(transactionId) } returns transaction

        val result = viewModel.getFormattedTransactionItem(transactionId)
        assertEquals(formattedTransaction, result)
    }

    @Test
    fun `calculateGst returns correct GST amount`() {
        val amount = BigDecimal(100)
        val expectedGst = BigDecimal("15.00")

        val result = viewModel.calculateGst(amount)
        assertEquals(expectedGst, result)
    }
}