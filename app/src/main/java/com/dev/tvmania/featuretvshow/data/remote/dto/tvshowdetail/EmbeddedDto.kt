package com.dev.tvmania.featuretvshow.data.remote.dto.tvshowdetail

import android.os.Parcelable
import com.dev.tvmania.featuretvshow.data.remote.dto.tvshowdetail.CastDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmbeddedDto(
    val cast: List<CastDto>
): Parcelable