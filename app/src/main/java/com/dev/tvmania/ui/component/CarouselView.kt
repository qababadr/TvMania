package com.dev.tvmania.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.dev.tvmania.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarouselView(
    urls: List<String>,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    pagerIndicatorColor: Color = MaterialTheme.colors.primary,
    shape: Shape = RectangleShape,
    crossFade: Int? = null
) {
    if(urls.isNotEmpty()){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            val pagerState = rememberPagerState(pageCount = urls.size)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = shape)
            ) { index ->
                NetworkImage(
                    url = urls[index],
                    crossFade = crossFade,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    onLoading = {
                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val indicator = createRef()
                            CircularProgressIndicator(
                                modifier = Modifier.constrainAs(indicator) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            )
                        }
                    },
                    onError = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_circle_info_solid),
                                contentDescription = "",
                                tint = Color.Red
                            )
                        }
                    }
                )
            }
            HorizontalPagerIndicator(
                activeColor = pagerIndicatorColor,
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(color = Color.Transparent)
                    .padding(bottom = 5.dp)
            )
        }
    }
}