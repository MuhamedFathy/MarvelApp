package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.marvelapp.R
import com.github.marvelapp.core.typealiases.Consumer
import com.github.marvelapp.presentation.activity.MainActivity
import com.github.marvelapp.presentation.composable.navigation.NavRoutes
import com.github.marvelapp.presentation.theme.DarkGrey
import com.github.marvelapp.presentation.theme.MarvelAppTheme
import com.github.marvelapp.presentation.viewmodel.uimodel.CharacterUiModel

@Composable
fun SearchScreen(
    navController: NavHostController? = null
) {

    val context = LocalContext.current as MainActivity
    val charactersViewModel = context.charactersViewModel
    val pagingData = charactersViewModel.charactersPagingState.collectAsLazyPagingItems()
    val searchData by charactersViewModel.searchDataState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            charactersViewModel.resetSearchData()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        SearchToolbar(
            navController = navController,
            textFieldChangesCallback = { charactersViewModel.findHeroesByName(it, pagingData.itemSnapshotList.items) }
        )

        LazyColumn(
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .padding(top = 4.dp)
                .background(color = DarkGrey)
        ) {
            items(
                count = searchData.size,
                key = { index -> searchData[index].id },
            ) {
                SearchItem(
                    item = searchData[it],
                    itemCallback = { uiModel ->
                        charactersViewModel.openSelectedCharacter(uiModel)
                        navController?.navigate(NavRoutes.CharacterDetailsScreen.route)
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.DarkGray.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
fun LazyItemScope.SearchItem(
    item: CharacterUiModel?,
    itemCallback: Consumer<CharacterUiModel?>? = null
) {
    val context = LocalContext.current
    val imageBuilder = remember { ImageRequest.Builder(context) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color.White)
            ) { itemCallback?.invoke(item) }
            .animateItem(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            model = imageBuilder
                .data(item?.thumbnail)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.marvel_mini_logo),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Spacer(Modifier.width(12.dp))

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp),
            text = item?.name.orEmpty(),
            color = Color.White.copy(alpha = 0.8f),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MarvelAppTheme {
        SearchScreen()
    }
}
