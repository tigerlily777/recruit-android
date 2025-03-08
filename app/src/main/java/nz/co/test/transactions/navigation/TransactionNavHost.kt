package nz.co.test.transactions.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import nz.co.test.transactions.navigation.TransactionDestination.TRANSACTION_DETAIL
import nz.co.test.transactions.navigation.TransactionDestination.TRANSACTION_ID_ARG
import nz.co.test.transactions.navigation.TransactionDestination.TRANSACTION_LIST
import nz.co.test.transactions.presentation.composable.TransactionListItemDetailsScreen
import nz.co.test.transactions.presentation.composable.TransactionListScreen

@Composable
fun TransactionNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TRANSACTION_LIST
    ) {
        composable(TRANSACTION_LIST) {
            TransactionListScreen(
                onTransactionClick = { formattedTransactionId ->
                    navController.navigate("$TRANSACTION_DETAIL/${formattedTransactionId}")
                }
            )
        }
        composable(
            route = "$TRANSACTION_DETAIL/{$TRANSACTION_ID_ARG}",
            arguments = listOf(navArgument(TRANSACTION_ID_ARG) { type = NavType.IntType }
            )
        ) {
            it.arguments?.getInt(TRANSACTION_ID_ARG)?.let { id ->
                TransactionListItemDetailsScreen(
                    formattedTransactionId = id
                )
            }
        }
    }
}
