package com.github.marvelapp.presentation.composable.navigation

sealed class NavRoutes(val route: String) {

    data object CharactersScreen : NavRoutes(route = "characters_screen")
    data object SearchScreen : NavRoutes(route = "search_screen")
    data object CharacterDetailsScreen : NavRoutes(route = "character_details_screen")
}
