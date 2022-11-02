package com.dev.tvmania.ui.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.tvmania.R
import com.dev.tvmania.ui.theme.SurfaceColor

@Composable
fun BookmarkButton(
    onClick: () -> Unit,
    inBookmarks: Boolean,
    buttonSize: Dp = 24.dp
) {

    val animatedSize by animateDpAsState(
        targetValue = if (inBookmarks) (buttonSize + 3.dp) else (buttonSize),
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        )
    )

    Card(
        modifier = Modifier.clickable(role = Role.Button) { onClick() },
        elevation = 8.dp,
        backgroundColor = if (isSystemInDarkTheme()) SurfaceColor else MaterialTheme.colors.surface,
        shape = RoundedCornerShape(size = 50.dp)
    ) {
        Icon(
            painter = painterResource(id = if (inBookmarks) R.drawable.bookmark_solid else R.drawable.bookmark_regular),
            contentDescription = "",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(all = 8.dp)
                .size(size = animatedSize)
        )
    }
}
