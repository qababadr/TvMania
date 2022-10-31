package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["tv_show_id", "person_id"],
    indices = [Index("person_id")]
)
data class TvShowPersonCrossRef(
    @ColumnInfo(name = "tv_show_id") val tvShowId: Long,
    @ColumnInfo(name = "person_id") val personId: Long
)