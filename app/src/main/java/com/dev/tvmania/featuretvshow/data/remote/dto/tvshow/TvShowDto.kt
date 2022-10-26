package com.dev.tvmania.featuretvshow.data.remote.dto.tvshow

import android.os.Parcelable
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDto(
    val id: Long,
    val name: String,
    val genres: List<String>,
    val rating: RatingDto,
    val image: ImageDto,
    val updated: Long,
): Parcelable{

    fun toTvShow(): TvShow {
        return TvShow(
            id = id,
            name = name,
            genres = genres,
            rating = rating.toRating(),
            image =  image.toImage(),
            updated = updated,
        )
    }

//    fun toTvShowEntity(): TvShowEntity{
//        return TvShowEntity(
//            id = id,
//            name = name,
//            rating = rating.average ?: 0.0,
//            genres = genres,
//            images = listOf(image.medium, image.original),
//            updated = updated
//        )
//    }
}