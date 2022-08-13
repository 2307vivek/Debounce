package dev.vivvvek.astro.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.vivvvek.astro.ui.details.DetailsScreen
import dev.vivvvek.astro.ui.home.AstroViewModel
import dev.vivvvek.astro.ui.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AstroNavigation(viewModel: AstroViewModel) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "HomeScreen",
    ) {
        composable(
            route = "HomeScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "DetailScreen" ->
                        scaleIn(
                            animationSpec = tween(300)
                        )+ fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "DetailScreen" ->
                        scaleOut(
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "DetailScreen" ->
                        scaleIn(
                            animationSpec = tween(300)
                        )+ fadeIn(animationSpec = tween(300))
                    else -> null
                }
            }
        ) {
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(
            route = "DetailScreen",
            enterTransition = {
                when (initialState.destination.route) {
                    "HomeScreen" ->
                        scaleIn(
                            animationSpec = tween(300)
                        )+ fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "HomeScreen" ->
                        scaleOut(
                            animationSpec = tween(300)
                        )+ fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "HomeScreen" ->
                        scaleOut(
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            }
        ) {
            DetailsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}