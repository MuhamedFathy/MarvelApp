package com.github.marvelapp.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.marvelapp.core.typealiases.Action
import com.github.marvelapp.presentation.theme.MarvelAppTheme

@Composable
fun CharactersScreen(
    itemCallback: Action = {}
) {
    CharacterItem()
}

@Composable
fun CharacterItem() {

}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    MarvelAppTheme {
        CharactersScreen()
    }
}
