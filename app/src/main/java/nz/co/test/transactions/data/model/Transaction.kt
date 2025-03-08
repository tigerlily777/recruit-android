package nz.co.test.transactions.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "id") val id: Int,
    @Json(name = "transactionDate") val transactionDate: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "debit") val debit: String,
    @Json(name = "credit") val credit: String
)