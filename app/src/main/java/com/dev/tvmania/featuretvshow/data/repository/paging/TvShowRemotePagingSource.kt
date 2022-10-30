package com.dev.tvmania.featuretvshow.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dev.tvmania.featuretvshow.data.remote.api.Api
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow

class TvShowRemotePagingSource(private val api: Api): PagingSource<Int, TvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {

        val currentPage = params.key ?: 1

        val response  = api.getTvShows(page = currentPage)

        val endOfPaginationReached = response.isEmpty()

        return try {
            LoadResult.Page(
                data = response.map { it.toTvShow() },
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey = if(endOfPaginationReached) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }

    }
}