package nz.co.test.transactions.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.domain.state.TransactionListState
import nz.co.test.transactions.domain.usecase.TransactionListUseCase
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val transactionListUseCase: TransactionListUseCase
) : ViewModel() {
    private val _transactionListUiState =
        MutableStateFlow<TransactionListState>(TransactionListState.Loading)
    val transactionListUiState: StateFlow<TransactionListState> =
        _transactionListUiState.asStateFlow()

    private val _transactionList = MutableStateFlow<List<FormattedTransaction>>(emptyList())

    fun getTransactionList() {
        if (_transactionList.value.isNotEmpty()) {
            return
        } else {
            viewModelScope.launch {
                transactionListUseCase.fetchTransactionList().collect { state ->
                    when (state) {
                        is TransactionListState.Loading -> {
                            _transactionListUiState.value = TransactionListState.Loading
                        }

                        is TransactionListState.Success -> {
                            _transactionList.value = state.formattedTransactionList
                            _transactionListUiState.value = TransactionListState.Success(
                                formattedTransactionList = state.formattedTransactionList,
                            )
                        }

                        is TransactionListState.Error -> {
                            _transactionListUiState.value = TransactionListState.Error(state.message)
                        }
                    }
                }
            }
        }
    }
}