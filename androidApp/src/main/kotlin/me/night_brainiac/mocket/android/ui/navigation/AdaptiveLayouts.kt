package me.night_brainiac.mocket.android.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import kotlinx.coroutines.launch
import me.night_brainiac.mocket.android.ui.base.EmptyScreen
import me.night_brainiac.mocket.android.ui.profile.connect_wallet.ConnectWalletScreen
import me.night_brainiac.mocket.android.util.*

@Composable
fun AdaptiveLayouts(windowSize: WindowSizeClass, displayFeatures: List<DisplayFeature>) {
    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) -> DevicePosture.BookPosture(foldingFeature.bounds)
        isSeparating(foldingFeature) -> DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
        else -> DevicePosture.NormalPosture
    }

    /**
     * This will help us select type of navigation and content type depending on window size and fold state of the device.
     */
    val navigationType: NavigationType
    val paneType: PaneType
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            paneType = PaneType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            paneType = if (foldingDevicePosture != DevicePosture.NormalPosture) PaneType.DUAL_PANE else PaneType.SINGLE_PANE
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType =
                if (foldingDevicePosture is DevicePosture.BookPosture) NavigationType.NAVIGATION_RAIL else NavigationType.PERMANENT_NAVIGATION_DRAWER
            paneType = PaneType.DUAL_PANE
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            paneType = PaneType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> NavigationContentPosition.TOP
        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> NavigationContentPosition.CENTER
        else -> NavigationContentPosition.TOP
    }

    NavigationWrapper(
        navigationType = navigationType,
        paneType = paneType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationWrapper(
    navigationType: NavigationType,
    paneType: PaneType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: NavRoute.HOME

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        // TODO check on custom width of PermanentNavigationDrawer: b/232495216
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }) {
            NavContent(
                navigationType = navigationType,
                paneType = paneType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    } else {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            NavContent(
                navigationType = navigationType,
                paneType = paneType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    }
}

@Composable
fun NavContent(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    paneType: PaneType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            NavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                paneType = paneType,
                displayFeatures = displayFeatures,
                navigationType = navigationType
            )
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                BottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@Composable
private fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    displayFeatures: List<DisplayFeature>,
    paneType: PaneType,
    navigationType: NavigationType
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.HOME
    ) {
        composable(NavRoute.HOME) {
            EmptyScreen()
        }
        composable(NavRoute.CHART) {
            EmptyScreen()
        }
        composable(NavRoute.PROFILE) {
            // TODO According to user info if exist to show screen of profile or connect wallet
            ConnectWalletScreen()
        }
        composable(NavRoute.MORE) {
            EmptyScreen()
        }
    }
}
