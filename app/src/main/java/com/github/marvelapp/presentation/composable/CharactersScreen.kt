package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.marvelapp.R
import com.github.marvelapp.core.typealiases.Action
import com.github.marvelapp.presentation.theme.MarvelAppTheme

@Composable
fun CharactersScreen(
    navController: NavHostController? = null
) {
    val windowInsets = WindowInsets.systemBars
    val innerPadding = windowInsets.asPaddingValues()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Spacer(
                    Modifier
                        .padding(innerPadding)
                        .height(64.dp)
                )
            }
            item { CharacterItem("3-D Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784/landscape_incredible.jpg") }
            item { CharacterItem("A-Bomb (HAS)", "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16/landscape_incredible.jpg") }
            item { CharacterItem("A.I.M", "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec/landscape_incredible.jpg") }
            item { CharacterItem("Abomination", "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04/landscape_incredible.jpg") }
            item { CharacterItem("3-D Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784/landscape_incredible.jpg") }
            item { CharacterItem("A-Bomb (HAS)", "https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16/landscape_incredible.jpg") }
            item { CharacterItem("A.I.M", "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec/landscape_incredible.jpg") }
            item { CharacterItem("Abomination", "https://i.annihil.us/u/prod/marvel/i/mg/9/50/4ce18691cbf04/landscape_incredible.jpg") }
        }

        Toolbar(navController)
    }
}

@Composable
fun CharacterItem(
    title: String,
    url: String,
    itemCallback: Action? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { itemCallback?.invoke() }
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.marvel_logo),
            error = painterResource(R.drawable.marvel_logo),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.2f))
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .drawBehind {
                    val path = Path().apply {
                        val width = size.width
                        val height = size.height
                        moveTo(width * 0.1f, 0f) // Top-left corner (shortened)
                        lineTo(width, 0f) // Top-right corner
                        lineTo(width * 0.9f, height) // Bottom-right corner (shortened)
                        lineTo(0f, height) // Bottom-left corner
                        close()
                    }
                    drawPath(path, color = Color.White)
                }
                .padding(horizontal = 14.dp, vertical = 4.dp),
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun CharactersScreenPreview() {
    MarvelAppTheme {
        CharactersScreen()
    }
}
