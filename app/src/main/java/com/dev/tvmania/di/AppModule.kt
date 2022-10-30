package com.dev.tvmania.di

import android.content.Context
import com.dev.tvmania.application.TvManiaApplication
import com.dev.tvmania.featuretvshow.data.remote.api.Api
import com.dev.tvmania.featuretvshow.data.repository.TvShowRepositoryImpl
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import com.dev.tvmania.featuretvshow.domain.usecase.GetTvShowDetail
import com.dev.tvmania.featuretvshow.domain.usecase.GetTvShows
import com.dev.tvmania.featuretvshow.domain.usecase.TvShowUseCases
import com.dev.tvmania.util.ApiBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesTvManiaApplication(
        @ApplicationContext app: Context
    ): TvManiaApplication {
        return app as TvManiaApplication
    }

    @Singleton
    @Provides
    fun providesApi(apiBuilder: ApiBuilder): Api{
        return apiBuilder.builder(api = Api::class.java)
    }


    @Singleton
    @Provides
    fun providesTvShowRepository(api: Api): TvShowRepository{
        return TvShowRepositoryImpl(api = api)
    }

    @Singleton
    @Provides
    fun providesTvShowUseCases(repository: TvShowRepository): TvShowUseCases{
        return TvShowUseCases(
            getTvShows = GetTvShows(repository = repository),
            getTvShowDetail = GetTvShowDetail(repository = repository)
        )
    }
}