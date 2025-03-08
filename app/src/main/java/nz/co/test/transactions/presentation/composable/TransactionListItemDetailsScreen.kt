package nz.co.test.transactions.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nz.co.test.transactions.presentation.TransactionListItemViewModel
import java.math.BigDecimal

@Composable
fun TransactionListItemDetailsScreen(
    formattedTransactionId: Int,
    transactionListItemViewModel: TransactionListItemViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val formattedTransaction = transactionListItemViewModel.getFormattedTransactionItem(formattedTransactionId)
        Text(
            text = "Transaction #$formattedTransactionId",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = formattedTransaction?.summary.orEmpty(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (formattedTransaction?.debit != BigDecimal.ZERO){
            Text(
                text = "-$${formattedTransaction?.debit}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (formattedTransaction != null) {
                Text(
                    text = "GST: ${transactionListItemViewModel.calculateGst(formattedTransaction.debit)}",
                    color = Color.Gray
                )
            }
        } else {
            Text(
                text = "+$${formattedTransaction?.credit}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Green
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (formattedTransaction != null) {
                Text(
                    text = "GST: ${transactionListItemViewModel.calculateGst(formattedTransaction.credit)}",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${formattedTransaction?.transactionDate} at ${formattedTransaction?.transactionTime}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}