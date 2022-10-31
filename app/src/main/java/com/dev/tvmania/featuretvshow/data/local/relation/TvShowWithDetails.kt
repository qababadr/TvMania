package com.dev.tvmania.featuretvshow.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.tvmania.featuretvshow.data.local.entity.PersonEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowDetailEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowPersonCrossRef
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Rating
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail

data class TvShowWithDetails(
    @Embedded val tvShow: TvShowEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "tv_show_id"
    )
    val detail: TvShowDetailEntity?,


    @Relation(
        entity = PersonEntity::class,
        parentColumn = "id",
        entityColumn = "person_id",
        associateBy = Junction(
            TvShowPersonCrossRef::class,
            parentColumn = "tv_show_id",
            entityColumn = "person_id"
        )
    )
    val actors: List<PersonEntity>
){
    fun toTvShowDetail(): TvShowDetail{
        return TvShowDetail(
            id  = tvShow.id,
            url = detail?.url ?: "",
            name = tvShow.name,
            genres = tvShow.genres,
            premiered = detail?.premiered ?: "",
            officialSite = detail?.officialSite,
            rating = Rating(average = tvShow.rating),
            image = Image(
                medium = tvShow.images.first(),
                original = tvShow.images.last()
            ),
            summary = detail?.summery ?: "",
            updated = tvShow.updated,
            actors = actors.map { it.toPerson() }
        )
    }
}
