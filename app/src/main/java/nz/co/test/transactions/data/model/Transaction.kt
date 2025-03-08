package nz.co.test.transactions.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "id") val id: Int,
    @Json(name = "transactionDate") val transactionDate: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "debit") val debit: String,
    @Json(name = "credit") val credit: String
)

data class FormattedTransaction(
    val id: Int,
    val transactionDate: LocalDate,
    val transactionTime: LocalTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
)

fun formatTransactionListItem(transaction: Transaction): FormattedTransaction {
    val transactionUtcTimeString = transaction.transactionDate + "Z"
    val offsetDateTime = OffsetDateTime.parse(transactionUtcTimeString)
    val zonedDateTime =
        offsetDateTime.withOffsetSameInstant(ZoneId.systemDefault().rules.getOffset(offsetDateTime.toInstant()))
            .toZonedDateTime()

    return FormattedTransaction(
        id = transaction.id,
        transactionDate = zonedDateTime.toLocalDate(),
        transactionTime = zonedDateTime.toLocalTime(),
        summary = transaction.summary,
        debit = BigDecimal(transaction.debit),
        credit = BigDecimal(transaction.credit)
    )
}