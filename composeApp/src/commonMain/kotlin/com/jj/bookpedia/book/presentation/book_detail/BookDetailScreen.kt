package com.jj.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.description_unavailable
import cmp_bookpedia.composeapp.generated.resources.languages
import cmp_bookpedia.composeapp.generated.resources.pages
import cmp_bookpedia.composeapp.generated.resources.rating
import cmp_bookpedia.composeapp.generated.resources.synopsis
import com.jj.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import com.jj.bookpedia.book.presentation.book_detail.components.BookChip
import com.jj.bookpedia.book.presentation.book_detail.components.CameraButton
import com.jj.bookpedia.book.presentation.book_detail.components.ChipSize
import com.jj.bookpedia.book.presentation.book_detail.components.TitledContent
import com.jj.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round


@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
    onCameraClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookDetailAction.OnBackClick -> onBackClick()
                is BookDetailAction.OnCameraClick -> {
                    state.book?.id?.let { bookId ->
                        onCameraClick(bookId)
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    onAction: (BookDetailAction) -> Unit,
    state: BookDetailState,
) {

    BlurredImageBackground(
        modifier = modifier.fillMaxSize(),
        imageUrl = state.book?.imageUrl,
        onBackClick = {
            onAction(BookDetailAction.OnBackClick)
        },
        onFavoriteClick = {
            onAction(BookDetailAction.OnFavoriteClick)
        },
        isFavorite = state.isFavorite,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(state.book != null) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 24.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.book.title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = state.book.authors.joinToString(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        state.book.averageRating?.let {
                            BookChip(
                                text = "${round(it * 10) / 10} ${stringResource(Res.string.rating)}",
                                size = ChipSize.SMALL,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = SandYellow
                                    )
                                }
                            )
                        }

                        state.book.numPages?.let {
                            BookChip(
                                text = "$it ${stringResource(Res.string.pages)}",
                                size = ChipSize.SMALL
                            )
                        }
                    }

                    FlowRow(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        maxItemsInEachRow = 3
                    ) {
                        state.book.languages.forEach { language ->
                            BookChip(
                                text = "$language ${stringResource(Res.string.languages)}",
                                size = ChipSize.SMALL
                            )
                        }
                    }

                    TitledContent(
                        title = stringResource(Res.string.synopsis),
                        content = {
                            Text(
                                text = state.book.description
                                    ?: stringResource(Res.string.description_unavailable),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
                
                // Show camera button only for favorited books
                if (state.isFavorite) {
                    CameraButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        onClick = {
                            onAction(BookDetailAction.OnCameraClick)
                        }
                    )
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }
        }
    }
}