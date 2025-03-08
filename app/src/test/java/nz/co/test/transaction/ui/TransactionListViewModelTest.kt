package nz.co.test.transactions.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.domain.state.TransactionListState
import nz.co.test.transactions.domain.usecase.TransactionListUseCase
import nz.co.test.transactions.presentation.TransactionListViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TransactionListViewModel
    private lateinit var transactionListUseCase: TransactionListUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        transactionListUseCase = mockk(relaxed = true)
        viewModel = TransactionListViewModel(transactionListUseCase)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getTransactionList sets Loading state initially`() = runTest {
        val stateFlow = MutableStateFlow<TransactionListState>(TransactionListState.Loading)
        coEvery { transactionListUseCase.fetchTransactionList()} returns stateFlow
        viewModel.getTransactionList()

        val result = viewModel.transactionListUiState.first()
        assertEquals(TransactionListState.Loading, result)
    }
}