package com.dev.tvmania.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import com.dev.tvmania.R
import com.dev.tvmania.ui.theme.Gray400
import com.dev.tvmania.ui.theme.RedErrorLight
import com.gowtham.ratingbar.RatingBar

@Composable
fun TvShowItem(
    tvShow: TvShow,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(tvShow.id) },
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = 0.35f),
            shape = MaterialTheme.shapes.large,
            elevation = 3.dp
        ) {
            NetworkImage(
                url = tvShow.image.medium,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onLoading = { CircularProgressIndicator() },
                onError = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_info_solid),
                        contentDescription = "",
                        tint = RedErrorLight
                    )
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tvShow.name,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            RatingBar(
                modifier = Modifier.padding(vertical = 8.dp),
                inactiveColor = Gray400,
                value = tvShow.rating.average.toFloat() / 2,
                onValueChange = {},
                onRatingChanged = {}
            )
            Row {
                tvShow.genres.forEach { genre ->
                    Chip(modifier = Modifier.padding(horizontal = 5.dp)) {
                        Text(
                            text = genre,
                            modifier = Modifier.padding(all = 5.dp),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }

}
