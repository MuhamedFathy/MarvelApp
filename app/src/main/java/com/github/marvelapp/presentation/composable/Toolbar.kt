@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.marvelapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.marvelapp.R
import com.github.marvelapp.core.typealiases.Consumer
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

@Composable
fun SearchToolbar(
    navController: NavHostController? = null,
    textFieldChangesCallback: Consumer<String>? = null
) {
    var textFieldValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black),
        title = {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = textFieldValue,
                onValueChange = {
                    textFieldChangesCallback?.invoke(it)
                    textFieldValue = it
                },
                shape = RoundedCornerShape(8.dp),
                label = {
                    Text(
                        stringResource(R.string.search_page_field_hint),
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                },
                textStyle = TextStyle(color = Color.Black),
                leadingIcon = {
                    Icon(Icons.Filled.Search, "", tint = Color.Black.copy(alpha = 0.5f))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
        },
        actions = {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple()
                    ) { navController?.popBackStack() }
                    .padding(8.dp),
                text = stringResource(R.string.search_page_button_cancel),
                color = Color.Red.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
fun CollapsedToolbar(
    navController: NavHostController? = null
) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    modifier = Modifier.offset(x = 0.1.dp, y = (-0.2).dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.Black.copy(alpha = 0.4f),
                    contentDescription = "Back"
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Back"
                )
            }
        }
    )
}
