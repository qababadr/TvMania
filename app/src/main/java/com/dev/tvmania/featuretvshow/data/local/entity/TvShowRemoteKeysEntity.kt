package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.tvmania.util.TV_SHOWS_REMOTE_KEY_TABLE

@Entity(tableName = TV_SHOWS_REMOTE_KEY_TABLE)
data class TvShowRemoteKeysEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "next_page") val nextPage: Int?,
    @ColumnInfo(name = "previous_page")val previousPage: Int?
)