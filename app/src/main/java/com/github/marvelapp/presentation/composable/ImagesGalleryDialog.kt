package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.github.marvelapp.R
import com.github.marvelapp.core.typealiases.Action
import com.github.marvelapp.presentation.activity.MainActivity
import com.github.marvelapp.presentation.theme.MarvelAppTheme

@Composable
fun ImagesGalleryDialog(
    onDismiss: Action? = null,
    closeCallback: Action? = null
) {
    val context = LocalContext.current as MainActivity
    val charactersDetailsViewModel = context.charactersDetailsViewModel
    val selectedProduct by charactersDetailsViewModel.selectedProduct.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(initialPage = selectedProduct.second, pageCount = { selectedProduct.first.size })

    Dialog(
        onDismissRequest = { onDismiss?.invoke() },
        properties = DialogProperties(dismissOnClickOutside = false, usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray.copy(alpha = 0.8f))
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp, end = 10.dp),
                onClick = { closeCallback?.invoke() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(22.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                key = { selectedProduct.first[it].id },
                pageSpacing = 20.dp,
                contentPadding = PaddingValues(horizontal = 32.dp),
                verticalAlignment = Alignment.Top
            ) { page ->

                Column {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.8f),
                        model = selectedProduct.first[page].thumbnail,
                        error = painterResource(R.drawable.marvel_mini_logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        text = selectedProduct.first[page].title,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        minLines = 3
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "${pagerState.currentPage + 1}/${selectedProduct.first.size}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.LightGray.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun ImagesGalleryDialogPreview() {
    MarvelAppTheme {
        ImagesGalleryDialog()
    }
}
