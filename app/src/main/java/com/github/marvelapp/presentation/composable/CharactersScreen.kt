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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.marvelapp.R
import com.github.marvelapp.core.typealiases.Consumer
import com.github.marvelapp.presentation.activity.MainActivity
import com.github.marvelapp.presentation.composable.navigation.NavRoutes
import com.github.marvelapp.presentation.theme.MarvelAppTheme
import com.github.marvelapp.presentation.viewmodel.uimodel.CharacterUiModel

@Composable
fun CharactersScreen(
    navController: NavHostController? = null
) {
    val windowInsets = WindowInsets.systemBars
    val innerPadding = windowInsets.asPaddingValues()
    val context = LocalContext.current as MainActivity
    val charactersViewModel = context.charactersViewModel
    val pagingData = charactersViewModel.charactersPagingState.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        charactersViewModel.getMarvelCharacters()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {

        if (pagingData.itemCount == 0) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .align(Alignment.Center)
                    .size(50.dp),
                color = Color.White
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .padding(innerPadding)
                            .height(64.dp)
                    )
                }

                items(
                    count = pagingData.itemCount,
                    key = { index -> pagingData.peek(index)?.id ?: "null at $index" },
                ) {
                    CharacterItem(
                        item = pagingData[it],
                        itemCallback = {
                            navController?.navigate(NavRoutes.CharacterDetailsScreen.route)
                        }
                    )
                }

                if (pagingData.loadState.append == LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .size(40.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Toolbar(navController)
    }
}

@Composable
fun LazyItemScope.CharacterItem(
    item: CharacterUiModel?,
    itemCallback: Consumer<CharacterUiModel?>? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { itemCallback?.invoke(item) }
            .animateItem()
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item?.thumbnail)
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
                .widthIn(min = 200.dp)
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .drawBehind {
                    val path = Path().apply {
                        val width = size.width
                        val height = size.height
                        moveTo(width * 0.06f, 0f) // Top-left corner (shortened)
                        lineTo(width - 20f, 0f) // Top-right corner
                        lineTo(width * 0.9f, height) // Bottom-right corner (shortened)
                        lineTo(0f, height) // Bottom-left corner
                        close()
                    }
                    drawPath(path, color = Color.White)
                }
                .padding(start = 20.dp, end = 25.dp, top = 4.dp, bottom = 4.dp),
            text = item?.name.orEmpty(),
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
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
