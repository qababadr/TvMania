package com.dev.tvmania.featuretvshow.domain.repository

import androidx.paging.PagingData
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshow.TvShow
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.TvShowDetail
import com.dev.tvmania.util.Resource
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {

    /**
     *  Get the list of tv shows with pagination from a remote data source
     *
     *  @param pageSize [Int] The amount of tv shows to be loaded  per page
     *  @return [Flow]<[PagingData]<[TvShow]>>
     */
    fun getTvShows(pageSize: Int): Flow<PagingData<TvShow>>


    /**
     * Get the detail about a tv show with cast
     *
     * @param id [Int] the id of the vt show
     * @return [Resource]<[TvShowDetail]>
     */
    suspend fun getTvShowDetail(id: Long): Resource<TvShowDetail?>


    /**
     *  Get 4 images to inject in carousel
     *
     *  @return [Flow]<[List]<[Image]>>
     */
    fun getCarouselImages(): Flow<List<Image>>

    /**
     *  Get the cached tv show detail from database
     *
     *  @param id [Long]
     *  @return [Flow]<[Resource]<[TvShowDetail]>>
     */
    fun getCachedTvShowDetail(id: Long): Flow<Resource<TvShowDetail>>

    /**
     *  Add or remove a tv show to/from bookmarks
     *
     *  @param tvShowId [Long]
     */
    suspend fun addOrRemoveTvShowBookmark(tvShowId: Long)

    /**
     *  Checks if the tv show is bookmarked or not
     *
     *  @param tvShowId [Long]
     *  @return [Flow]<[Boolean]>
     */
    fun inBookMarks(tvShowId: Long): Flow<Boolean>
}