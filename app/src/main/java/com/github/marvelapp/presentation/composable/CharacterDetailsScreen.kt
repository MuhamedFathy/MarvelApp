package com.github.marvelapp.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.marvelapp.presentation.theme.MarvelAppTheme

@Composable
fun CharacterDetailsScreen() {

}

@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    MarvelAppTheme {
        CharacterDetailsScreen()
    }
}
