package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.marvelapp.presentation.theme.DarkGrey
import com.github.marvelapp.presentation.theme.MarvelAppTheme

@Composable
fun SearchScreen(
    navController: NavHostController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        SearchToolbar(navController)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp)
                .background(color = DarkGrey)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MarvelAppTheme {
        SearchScreen()
    }
}
