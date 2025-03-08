package nz.co.test.transactions.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.domain.state.TransactionListState
import nz.co.test.transactions.presentation.TransactionListViewModel
import java.math.BigDecimal

@Composable
fun TransactionListScreen(
    viewModel: TransactionListViewModel = hiltViewModel(),
    onTransactionClick: (Int) -> Unit = {}
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
                transactionList = (transactionListState as TransactionListState.Success).formattedTransactionList,
                onTransactionClick = onTransactionClick
            )
        }

        is TransactionListState.Error -> {
            Text(text = "Error: ${(transactionListState as TransactionListState.Error).message}")
        }
    }
}

@Composable
fun TransactionList(
    transactionList: List<FormattedTransaction>,
    onTransactionClick: (Int) -> Unit
) {
    LazyColumn {
        item(transactionList) {
            transactionList.forEach { transaction ->
                TransactionListItem(
                    formattedTransaction = transaction,
                    modifier = Modifier.clickable {
                        onTransactionClick(
                            transaction.id
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionListItem(
    formattedTransaction: FormattedTransaction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${formattedTransaction.transactionDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(
                        text = formattedTransaction.summary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                if (formattedTransaction.debit > BigDecimal.ZERO) {
                    Text(
                        text = "-$${formattedTransaction.debit}",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (formattedTransaction.credit > BigDecimal.ZERO) {
                    Text(
                        text = "+$${formattedTransaction.credit}",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
