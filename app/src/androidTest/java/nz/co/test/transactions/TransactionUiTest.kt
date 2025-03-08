package nz.co.test.transactions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import nz.co.test.transactions.data.model.FormattedTransaction
import nz.co.test.transactions.presentation.composable.TransactionList
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

class TransactionUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val list = listOf(
        FormattedTransaction(
            id = 1,
            transactionDate = LocalDate.parse("2021-01-01"),
            transactionTime = LocalTime.parse("12:00:00"),
            summary = "Test 1",
            debit = BigDecimal("100.0"),
            credit = BigDecimal("0.0")
        ),
        FormattedTransaction(
            id = 2,
            transactionDate = LocalDate.parse("2021-01-02"),
            transactionTime = LocalTime.parse("12:00:00"),
            summary = "Test 2",
            debit = BigDecimal("0.0"),
            credit = BigDecimal("100.0")
        )
    )

    @Test
    fun testTransaction() {
        composeTestRule.setContent {
            TransactionList(transactionList = list) {
                composeTestRule.onNodeWithText("Test 1")
                    .performClick()
                composeTestRule.onNodeWithText("GST").assertIsDisplayed()
            }
        }
    }
}