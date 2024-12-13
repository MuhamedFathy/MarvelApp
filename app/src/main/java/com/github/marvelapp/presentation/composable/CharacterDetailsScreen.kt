package com.github.marvelapp.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.marvelapp.R
import com.github.marvelapp.core.extensions.openUrlInBrowser
import com.github.marvelapp.core.typealiases.Consumer
import com.github.marvelapp.domain.holder.DataHolder
import com.github.marvelapp.presentation.activity.MainActivity
import com.github.marvelapp.presentation.theme.MarvelAppTheme
import com.github.marvelapp.presentation.viewmodel.CharactersDetailsViewModel
import com.github.marvelapp.presentation.viewmodel.uimodel.CharacterUiModel
import com.github.marvelapp.presentation.viewmodel.uimodel.UrlUiModel
import com.github.marvelapp.presentation.viewmodel.uimodel.toUiModel
import java.util.Locale

@Composable
fun CharacterDetailsScreen(
    charactersDetailsViewModel: CharactersDetailsViewModel = hiltViewModel(),
    navController: NavHostController? = null
) {
    val context = LocalContext.current as MainActivity
    val charactersViewModel = context.charactersViewModel
    val character by charactersViewModel.selectedCharacter.collectAsStateWithLifecycle()
    val imageBuilder = remember { ImageRequest.Builder(context) }
    val scrollState = rememberScrollState()

    LaunchedEffect(character) {
        charactersDetailsViewModel.getCharacterComics(character?.id ?: return@LaunchedEffect)
        charactersDetailsViewModel.getCharacterSeries(character?.id ?: return@LaunchedEffect)
        charactersDetailsViewModel.getCharacterStories(character?.id ?: return@LaunchedEffect)
        charactersDetailsViewModel.getCharacterEvents(character?.id ?: return@LaunchedEffect)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .parallaxLayoutModifier(scrollState, 2),
                model = imageBuilder
                    .data(character?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painter = rememberAsyncImagePainter(
                            model = imageBuilder
                                .data(character?.thumbnail)
                                .crossfade(true)
                                .build()
                        ),
                        contentScale = ContentScale.Crop
                    )
                    .background(color = Color.Black.copy(alpha = 0.9f))
                    .padding(vertical = 20.dp)
                    .animateContentSize()
            ) {
                if (character == null) return
                with(character!!) {
                    NameSection(name)
                    Spacer(modifier = Modifier.height(12.dp))
                    DescriptionSection(description)
                    CharactersProducts(charactersDetailsViewModel)
                    RelatedLinksSection(
                        urls = urls,
                        urlCallback = { url -> context.openUrlInBrowser(url) }
                    )
                }
            }
        }

        CollapsedToolbar(navController = navController)
    }
}

@Composable
fun NameSection(name: String?) {
    if (name.isNullOrBlank()) return
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = stringResource(R.string.details_page_name).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = name,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
fun DescriptionSection(description: String?) {
    if (description.isNullOrBlank()) return
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = stringResource(R.string.details_page_description).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = buildAnnotatedString { append(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)) },
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
fun CharactersProducts(
    charactersDetailsViewModel: CharactersDetailsViewModel
) {
    val comicsDataState by charactersDetailsViewModel.comicsDataState.collectAsStateWithLifecycle()
    val seriesDataState by charactersDetailsViewModel.seriesDataState.collectAsStateWithLifecycle()
    val storiesDataState by charactersDetailsViewModel.storiesDataState.collectAsStateWithLifecycle()
    val eventsDataState by charactersDetailsViewModel.eventsDataState.collectAsStateWithLifecycle()

    if (comicsDataState is DataHolder.Loading) {
        Spacer(modifier = Modifier.height(22.dp))

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
    } else if (comicsDataState is DataHolder.Success) {
        val comics = (comicsDataState as DataHolder.Success).data?.map { it.toUiModel() }.orEmpty()
        CharacterProductsSection(
            title = R.string.details_page_comics,
            products = comics
        )
    }

    if (seriesDataState is DataHolder.Loading) {
        Spacer(modifier = Modifier.height(22.dp))

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
    } else if (seriesDataState is DataHolder.Success) {
        val series = (seriesDataState as DataHolder.Success).data?.map { it.toUiModel() }.orEmpty()
        CharacterProductsSection(
            title = R.string.details_page_series,
            products = series
        )
    }

    if (storiesDataState is DataHolder.Loading) {
        Spacer(modifier = Modifier.height(22.dp))

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
    } else if (storiesDataState is DataHolder.Success) {
        val stories = (storiesDataState as DataHolder.Success).data?.map { it.toUiModel() }.orEmpty()
        CharacterProductsSection(
            title = R.string.details_page_stories,
            products = stories
        )
    }

    if (eventsDataState is DataHolder.Loading) {
        Spacer(modifier = Modifier.height(22.dp))

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
    } else if (eventsDataState is DataHolder.Success) {
        val events = (eventsDataState as DataHolder.Success).data?.map { it.toUiModel() }.orEmpty()
        CharacterProductsSection(
            title = R.string.details_page_events,
            products = events
        )
    }
}

@Composable
fun CharacterProductsSection(
    @StringRes title: Int,
    products: List<CharacterUiModel>
) {
    if (products.isEmpty()) return

    Spacer(modifier = Modifier.height(22.dp))

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = stringResource(title).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier) }

        items(items = products, key = { it.id }) {
            CharacterProductItem(it)
        }

        item { Spacer(modifier = Modifier) }
    }
}

@Composable
fun CharacterProductItem(
    item: CharacterUiModel
) {
    val context = LocalContext.current
    val imageBuilder = remember { ImageRequest.Builder(context) }

    Column(
        modifier = Modifier
            .width(width = 100.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = imageBuilder
                .data(item.thumbnail)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.marvel_mini_logo),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            minLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RelatedLinksSection(
    urls: List<UrlUiModel>,
    urlCallback: Consumer<String>
) {
    if (urls.isEmpty()) return

    Spacer(modifier = Modifier.height(22.dp))

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = stringResource(R.string.details_page_related_links).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    repeat(urls.size) { index ->
        val url = urls[index]

        if (url.type.isBlank()) return

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = Color.White)
                ) { urlCallback.invoke(url.url) }
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = url.type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = null,
            )
        }
    }
}


fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int) =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }

@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    MarvelAppTheme {
        CharacterDetailsScreen()
    }
}
