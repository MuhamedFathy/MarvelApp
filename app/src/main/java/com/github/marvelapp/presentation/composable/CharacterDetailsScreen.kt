package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.github.marvelapp.R
import com.github.marvelapp.core.extensions.openUrlInBrowser
import com.github.marvelapp.core.typealiases.Consumer
import com.github.marvelapp.presentation.activity.MainActivity
import com.github.marvelapp.presentation.theme.MarvelAppTheme
import com.github.marvelapp.presentation.viewmodel.uimodel.UrlUiModel
import java.util.Locale

@Composable
fun CharacterDetailsScreen(
    navController: NavHostController? = null
) {
    val context = LocalContext.current as MainActivity
    val charactersViewModel = context.charactersViewModel
    val character by charactersViewModel.selectedCharacter.collectAsStateWithLifecycle()
    val imageBuilder = remember { ImageRequest.Builder(context) }
    val scrollState = rememberScrollState()

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
                    .background(color = Color.Black)
                    .padding(horizontal = 8.dp, vertical = 20.dp)
            ) {
                if (character == null) return
                with(character!!) {
                    NameSection(name)
                    Spacer(modifier = Modifier.height(12.dp))
                    DescriptionSection(description)
                    Spacer(modifier = Modifier.height(22.dp))

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
        text = stringResource(R.string.details_page_name).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = name,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
fun DescriptionSection(description: String?) {
    if (description.isNullOrBlank()) return
    Text(
        text = stringResource(R.string.details_page_description).uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.Red.copy(alpha = 0.7f)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Composable
fun RelatedLinksSection(
    urls: List<UrlUiModel>,
    urlCallback: Consumer<String>
) {
    if (urls.isEmpty()) return

    Text(
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
                .padding(vertical = 12.dp),
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
