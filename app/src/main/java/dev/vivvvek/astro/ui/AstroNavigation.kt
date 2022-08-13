/*
 * Copyright 2021 Vivek Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.vivvvek.astro.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
                        scaleIn() + fadeIn()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "DetailScreen" ->
                        scaleOut() + fadeOut()
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    "DetailScreen" ->
                        scaleIn() + fadeIn()
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
                        scaleIn() + fadeIn()
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "HomeScreen" ->
                        scaleOut() + fadeOut()
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    "HomeScreen" ->
                        scaleOut() + fadeOut()
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
