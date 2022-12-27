package me.night_brainiac.mocket.android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import me.night_brainiac.mocket.android.R

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

object NavRoute {
    const val HOME = "home"
    const val CHART = "chart"
    const val PROFILE = "profile"
    const val MORE = "more"
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = NavRoute.HOME,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.nav_home
    ),
    TopLevelDestination(
        route = NavRoute.CHART,
        selectedIcon = Icons.Filled.Assessment,
        unselectedIcon = Icons.Outlined.Assessment,
        iconTextId = R.string.nav_chart
    ),
    TopLevelDestination(
        route = NavRoute.PROFILE,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        iconTextId = R.string.nav_profile
    ),
    TopLevelDestination(
        route = NavRoute.MORE,
        selectedIcon = Icons.Filled.Widgets,
        unselectedIcon = Icons.Outlined.Widgets,
        iconTextId = R.string.nav_more
    )
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination when selecting the same item again
            launchSingleTop = true

            // Restore state when selecting a previously selected item again
            restoreState = true
        }
    }
}
