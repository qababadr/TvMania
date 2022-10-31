package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Rating
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import com.dev.tvmania.util.TV_SHOWS_TABLE

@Entity(tableName = TV_SHOWS_TABLE)
data class TvShowEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val rating: Double,
    val genres: List<String>,
    val images: List<String>,
    val updated: Long
){

    fun toTvShow(): TvShow{
        return TvShow(
            id = id,
            name = name,
            rating = Rating(average = rating),
            genres = genres,
            image = Image(
                medium = images.first(),
                original = images.last()
            ),
            updated = updated
        )
    }

}