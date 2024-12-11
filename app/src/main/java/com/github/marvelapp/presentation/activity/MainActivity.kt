package com.github.marvelapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.marvelapp.R
import com.github.marvelapp.presentation.composable.navigation.AppNavHost
import com.github.marvelapp.presentation.composable.navigation.NavRoutes
import com.github.marvelapp.presentation.theme.MarvelAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvelAppTheme { AppContent() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute == NavRoutes.SearchScreen.route) return@Scaffold
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(id = R.drawable.marvel_logo),
                        contentDescription = "Marvel logo"
                    )
                },
                navigationIcon = {
                    if (currentRoute != NavRoutes.CharactersScreen.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (currentRoute == NavRoutes.CharactersScreen.route) {
                        IconButton(
                            onClick = {
                                navController.navigate(NavRoutes.SearchScreen.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        AppNavHost(
            modifier = Modifier.padding(contentPadding),
            navController = navController
        )
    }
}
