package com.dev.tvmania.featuretvshow.data.remote.dto.tvshow

import android.os.Parcelable
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDto(
    val medium: String,
    val original: String
): Parcelable{

    fun toImage(): Image {
        return Image(
            medium,
            original
        )
    }

}