package com.github.marvelapp.presentation.composable.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.marvelapp.presentation.composable.CharacterDetailsScreen
import com.github.marvelapp.presentation.composable.CharactersScreen
import com.github.marvelapp.presentation.composable.SearchScreen

@Composable
fun AppNavHost(
    startDestination: String = NavRoutes.CharactersScreen.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
    ) {
        composable(route = NavRoutes.CharactersScreen.route) {
            CharactersScreen(navController = navController)
        }

        composable(route = NavRoutes.CharacterDetailsScreen.route) {
            CharacterDetailsScreen(navController = navController)
        }

        composable(route = NavRoutes.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
    }
}
