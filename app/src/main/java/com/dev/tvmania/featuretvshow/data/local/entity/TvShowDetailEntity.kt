package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.tvmania.util.TV_SHOWS_DETAIL_TABLE

@Entity(
    tableName = TV_SHOWS_DETAIL_TABLE,
    indices = [Index("tv_show_id", unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = TvShowEntity::class,
            parentColumns = ["id"],
            childColumns = ["tv_show_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TvShowDetailEntity(
    @ColumnInfo(name = "tv_show_detail_id") @PrimaryKey(autoGenerate = true) val tvShowDetailId: Long? = null,
    @ColumnInfo(name = "tv_show_id") val tvShowId: Long,
    val url: String,
    val premiered: String,
    val officialSite: String?,
    val summery: String
)