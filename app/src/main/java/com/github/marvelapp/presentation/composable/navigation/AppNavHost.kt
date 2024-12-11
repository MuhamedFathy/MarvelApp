package com.github.marvelapp.presentation.composable.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.marvelapp.presentation.composable.CharacterDetailsScreen
import com.github.marvelapp.presentation.composable.CharactersScreen
import com.github.marvelapp.presentation.composable.SearchScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.CharactersScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavRoutes.CharactersScreen.route) {
            CharactersScreen(
                itemCallback = { navController.navigate(NavRoutes.CharacterDetailsScreen.route) }
            )
        }

        composable(route = NavRoutes.CharacterDetailsScreen.route) {
            CharacterDetailsScreen()
        }

        composable(route = NavRoutes.SearchScreen.route) {
            SearchScreen()
        }
    }
}
