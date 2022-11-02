package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dev.tvmania.util.BOOKMARKS_TABLE

@Entity(
    tableName = BOOKMARKS_TABLE,
    indices = [Index("tv_show_id", unique = true)]
)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "tv_show_id") val tvShowId: Long
)