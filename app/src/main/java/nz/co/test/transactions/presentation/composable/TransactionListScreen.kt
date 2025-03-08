package nz.co.test.transactions.presentation.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import nz.co.test.transactions.data.model.Transaction
import nz.co.test.transactions.domain.state.TransactionListState
import nz.co.test.transactions.presentation.TransactionListViewModel

@Composable
fun TransactionListScreen(
    viewModel: TransactionListViewModel = hiltViewModel()
) {
    val transactionListState by viewModel.transactionListUiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getTransactionList()
    }

    when (transactionListState) {
        is TransactionListState.Loading -> {
            Text(text = "This is a beautiful loading screen")
        }

        is TransactionListState.Success -> {
            TransactionList(
                transactionList = (transactionListState as TransactionListState.Success).transactionList
            )
        }

        is TransactionListState.Error -> {
            Text(text = "Error: ${(transactionListState as TransactionListState.Error).message}")
        }
    }
}

@Composable
fun TransactionList(
    transactionList: List<Transaction>
) {
    LazyColumn {
        item(transactionList) {
            transactionList.forEach { transaction ->
                TransactionListItem(
                    transactionItem = transaction
                )
            }
        }
    }
}

@Composable
fun TransactionListItem(
    transactionItem: Transaction
) {
    Text(text = transactionItem.id.toString())
}
