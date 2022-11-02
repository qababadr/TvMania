package com.dev.tvmania.featuretvshow.data.local.dao

import androidx.room.*
import com.dev.tvmania.featuretvshow.data.local.entity.BookmarkEntity
import com.dev.tvmania.util.BOOKMARKS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Query("DELETE FROM $BOOKMARKS_TABLE WHERE tv_show_id=:id")
    suspend fun deleteBookmark(id: Long)

    @Query("SELECT EXISTS(SELECT * FROM $BOOKMARKS_TABLE WHERE tv_show_id=:id)")
    fun inBookmarks(id: Long): Flow<Boolean>
}