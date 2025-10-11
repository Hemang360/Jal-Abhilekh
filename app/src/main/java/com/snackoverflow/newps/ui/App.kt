package com.snackoverflow.newps.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.snackoverflow.newps.ui.screens.AlertsScreenUI
import com.snackoverflow.newps.ui.screens.AnalyticsTrendsScreenUI
import com.snackoverflow.newps.ui.screens.CaptureReadingScreen
import com.snackoverflow.newps.ui.screens.DetailedSiteViewScreen
import com.snackoverflow.newps.ui.screens.HistoryScreenUI
import com.snackoverflow.newps.ui.screens.LoginPage
import com.snackoverflow.newps.ui.screens.ManualReadingEntryScreenUI
import com.snackoverflow.newps.ui.screens.OfflineQueueScreenEnhanced
import com.snackoverflow.newps.ui.screens.QRValidationScreenUI
import com.snackoverflow.newps.ui.screens.ReadingDetailScreenUI
import com.snackoverflow.newps.ui.screens.ReadingSummaryScreen
import com.snackoverflow.newps.ui.screens.SettingsProfileHelpScreen
import com.snackoverflow.newps.ui.screens.SupervisorDashboardScreenUI

sealed class Screen(val route : String) {
    object AlertScreen : Screen("alert")
    object AnalyticsScreen : Screen("analytics")
    object CaptureReading : Screen("capture")
    object DashboardScreen : Screen("dashboard")
    object History : Screen("history")
    object Login : Screen("login")
    object Manual : Screen("manual")
    object MapScreen : Screen("map")
    object ReadingSummary : Screen("summary")
    object Settings : Screen("settings")
    object SiteView : Screen("site")
    object SyncData : Screen("sync")
    object Verification : Screen("verification")
}
@Composable
fun JalAbhilekh() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginPage(navController)
        }
        composable(Screen.AlertScreen.route) {
            AlertsScreenUI(navController)
        }
        composable(Screen.AnalyticsScreen.route) {
            AnalyticsTrendsScreenUI(navController)
        }
        composable(Screen.CaptureReading.route) {
            CaptureReadingScreen(navController)
        }
        composable(Screen.DashboardScreen.route) {
            SupervisorDashboardScreenUI(navController)
        }
        composable(Screen.History.route) {
            HistoryScreenUI(navController)
        }
        composable(Screen.Manual.route) {
            ManualReadingEntryScreenUI(navController)
        }
        composable(Screen.MapScreen.route) {
            QRValidationScreenUI(navController)
        }
        composable(Screen.ReadingSummary.route) {
            ReadingSummaryScreen(navController)
        }
        composable(Screen.Settings.route) {
            SettingsProfileHelpScreen(navController)
        }
        composable(Screen.SiteView.route) {
            DetailedSiteViewScreen(navController)
        }
        composable(Screen.SyncData.route) {
            OfflineQueueScreenEnhanced(navController)
        }
        composable(Screen.Verification.route) {
            ReadingDetailScreenUI(navController)
        }
    }
}