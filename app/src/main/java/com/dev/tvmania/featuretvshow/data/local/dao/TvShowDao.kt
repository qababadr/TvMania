package com.dev.tvmania.featuretvshow.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowDetailEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowPersonCrossRef
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowRemoteKeysEntity
import com.dev.tvmania.util.TV_SHOWS_DETAIL_TABLE
import com.dev.tvmania.util.TV_SHOWS_REMOTE_KEY_TABLE
import com.dev.tvmania.util.TV_SHOWS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowDetail(tvShowDetail: TvShowDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowPersonCrossRef(crossRefs: List<TvShowPersonCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShowRemoteKeys(keys: List<TvShowRemoteKeysEntity>)

    @Query("DELETE FROM $TV_SHOWS_TABLE")
    suspend fun clearTvShowsTable()

    @Query("DELETE FROM $TV_SHOWS_DETAIL_TABLE")
    suspend fun clearTvShowDetailTable()

    @Query("DELETE FROM $TV_SHOWS_REMOTE_KEY_TABLE")
    suspend fun clearTvShowRemoteKeysTable()

    @Query("SELECT * FROM $TV_SHOWS_REMOTE_KEY_TABLE WHERE id=:id")
    suspend fun getTvShowRemoteKey(id: Long): TvShowRemoteKeysEntity

    @Query("SELECT * FROM $TV_SHOWS_TABLE")
    fun getTvShows(): PagingSource<Int, TvShowEntity>

    @Query("SELECT * FROM $TV_SHOWS_TABLE ORDER By random() LIMIT:limit")
    fun getRandomTvShows(limit: Int): Flow<List<TvShowEntity>>
}