package nz.co.test.transaction.ui.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.domain.state.TransactionListState
import nz.co.test.transactions.domain.usecase.TransactionListUseCase
import nz.co.test.transactions.presentation.TransactionListViewModel
import nz.co.test.transactions.presentation.composable.TransactionListScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class TransactionListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var transactionListUseCase: TransactionListUseCase
    private lateinit var viewModel: TransactionListViewModel

    @Before
    fun setUp() {
        transactionListUseCase = mockk(relaxed = true)
        viewModel = TransactionListViewModel(transactionListUseCase)
    }

    @Test
    fun testTransactionListItemIsDisplayed() = runTest {
        val transaction = FormattedTransaction(
            id = 1,
            transactionDate = LocalDate.of(2023, 10, 1),
            transactionTime = LocalTime.of(12, 0, 0),
            summary = "Test Transaction",
            debit = BigDecimal.ZERO,
            credit = BigDecimal(100)
        )
        coEvery {  transactionListUseCase.fetchTransactionList()} returns flow {
            emit(TransactionListState.Success(listOf(transaction)))
        }
        composeTestRule.setContent {
            TransactionListScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Test Transaction").assertIsDisplayed()
        composeTestRule.onNodeWithText("100.00").assertIsDisplayed()
    }

    @Test
    fun testTransactionListItemClick() = runTest {
        val transaction = FormattedTransaction(
            id = 1,
            transactionDate = LocalDate.of(2023, 10, 1),
            transactionTime = LocalTime.of(12, 0, 0),
            summary = "Test Transaction",
            debit = BigDecimal.ZERO,
            credit = BigDecimal(100)
        )
        coEvery {  transactionListUseCase.fetchTransactionList()} returns flow {
            emit(TransactionListState.Success(listOf(transaction)))
        }
        var clickedTransactionId: Int? = null

        composeTestRule.setContent {
            TransactionListScreen(
                viewModel = viewModel,
                onTransactionClick = { id -> clickedTransactionId = id }
            )
        }

        composeTestRule.onNodeWithText("Test Transaction").assertIsDisplayed()
        assert(clickedTransactionId == 1)
    }
}