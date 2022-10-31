package com.dev.tvmania.featuretvshow.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.dev.tvmania.R
import com.dev.tvmania.ui.component.CarouselView
import com.dev.tvmania.ui.component.PaginationStateHandler
import com.dev.tvmania.ui.component.TvShowItem
import com.dev.tvmania.ui.component.WarningMessage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTvShowClick: (Long) -> Unit
) {

    val tvShowsState = viewModel.tvShowsState
        .collectAsLazyPagingItems()

    val carouselImages by viewModel.carouselImagesState
        .collectAsState()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 25.dp),
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxSize()
    ) {

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                Image(
                    painter = painterResource(id = R.drawable.tv_mania),
                    contentDescription = "",
                    modifier = Modifier
                        .requiredWidth(width = 120.dp)
                        .padding(top = 12.dp),
                )
            }
        }

        item {
            CarouselView(
                modifier = Modifier.requiredHeight(height = 260.dp)
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                urls = carouselImages.map { it.original },
                shape = MaterialTheme.shapes.large,
                crossFade = 1000
            )
        }

        items(
            items = tvShowsState,
            key = { it.id }
        ) {
            it?.let { tvShow ->
                TvShowItem(
                    tvShow = tvShow,
                    modifier = Modifier.requiredHeight(height = 150.dp),
                    onClick = { onTvShowClick(it) }
                )
            }
        }

        item {
            PaginationStateHandler(
                paginationState = tvShowsState,
                loadingComponent = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                errorComponent = {
                    WarningMessage(
                        text = stringResource(id = R.string.err_loading_tv_shows),
                        trailingContent = {
                            Text(
                                text  = stringResource(id = R.string.lbl_retry),
                                modifier = Modifier
                                    .padding(start = 3.dp)
                                    .clickable(role = Role.Button) { tvShowsState.retry() },
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    )
                }
            )
        }
    }
}
