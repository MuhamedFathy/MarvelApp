@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.marvelapp.R
import com.github.marvelapp.presentation.composable.navigation.NavRoutes

@Composable
fun Toolbar(
    navController: NavHostController? = null
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.6f),
            scrolledContainerColor = Color.Black.copy(alpha = 0.6f)
        ),
        title = {
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.marvel_logo),
                contentDescription = "Marvel logo"
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navController?.navigate(NavRoutes.SearchScreen.route)
                }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Search,
                    tint = Color.Red.copy(alpha = 0.7f),
                    contentDescription = "Search"
                )
            }
        }
    )
}
