package com.dev.tvmania.featuretvshow.data.remote.dto.tvshow

import android.os.Parcelable
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingDto(
    val average: Double?
): Parcelable{

    fun toRating(): Rating {
        return Rating(average = average ?: 0.0)
    }
}