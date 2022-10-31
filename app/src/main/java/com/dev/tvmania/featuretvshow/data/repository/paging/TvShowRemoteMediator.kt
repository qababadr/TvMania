package com.dev.tvmania.featuretvshow.data.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dev.tvmania.featuretvshow.data.local.database.TvManiaDatabase
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowEntity
import com.dev.tvmania.featuretvshow.data.local.entity.TvShowRemoteKeysEntity
import com.dev.tvmania.featuretvshow.data.remote.api.Api

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator(
    private val api: Api,
    private val database: TvManiaDatabase
) : RemoteMediator<Int, TvShowEntity>() {

    private val tvShowDao = database.tvShowDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowEntity>
    ): MediatorResult {

        return try {

            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    getNextPageClosestToCurrentPosition(state = state)?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeysPreviousPage = getPreviousPageForTheFirstItem(state = state)
                    val previousPage = remoteKeysPreviousPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeysPreviousPage != null
                    )
                    previousPage
                }

                LoadType.APPEND -> {
                    val remoteKeysNextPage = getNextPageForTheLastItem(state = state)
                    val nextPage = remoteKeysNextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeysNextPage != null
                    )
                    nextPage
                }
            }

            val tvShows = api.getTvShows(page = currentPage)

            val endOfPaginationReached = tvShows.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                try {

                    if(loadType == LoadType.REFRESH){
                        deleteCache()
                    }

                    val keys = tvShows.map { tvShow ->
                        TvShowRemoteKeysEntity(
                            id = tvShow.id,
                            nextPage = nextPage,
                            previousPage = prevPage
                        )
                    }

                    tvShowDao.insertTvShowRemoteKeys(keys = keys)

                    tvShowDao.insertTvShows(tvShows = tvShows.map { it.toTvShowEntity() })

                } catch (exp: Exception) {
                    exp.stackTrace.forEach { stackTraceElement ->
                        Log.d(
                            "exception",
                            "file name: ${stackTraceElement.fileName} class name: ${stackTraceElement.className}"
                        )
                    }
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exp: Exception) {
            MediatorResult.Error(exp)
        }

    }

    private suspend fun getNextPageClosestToCurrentPosition(state: PagingState<Int, TvShowEntity>): Int? {
        val position = state.anchorPosition
        val entity = position?.let { state.closestItemToPosition(it) }
        return entity?.id?.let { tvShowDao.getTvShowRemoteKey(id = it).nextPage }
    }

    private suspend fun getPreviousPageForTheFirstItem(state: PagingState<Int, TvShowEntity>): Int? {
        val loadResult = state.pages.firstOrNull { it.data.isNotEmpty() }
        val entity = loadResult?.data?.firstOrNull()
        return entity?.let { tvShowEntity -> tvShowDao.getTvShowRemoteKey(id = tvShowEntity.id).previousPage }
    }

    private suspend fun getNextPageForTheLastItem(state: PagingState<Int, TvShowEntity>): Int? {
        val loadResult = state.pages.lastOrNull { it.data.isNotEmpty() }
        val entity = loadResult?.data?.lastOrNull()
        return entity?.let { tvShowEntity -> tvShowDao.getTvShowRemoteKey(id = tvShowEntity.id).nextPage }
    }

    private suspend fun deleteCache(){
        tvShowDao.clearTvShowsTable()
        tvShowDao.clearTvShowRemoteKeysTable()
    }
}