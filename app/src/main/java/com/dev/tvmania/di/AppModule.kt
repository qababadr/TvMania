package com.dev.tvmania.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dev.tvmania.application.TvManiaApplication
import com.dev.tvmania.featuretvshow.data.local.database.ControlledConverter
import com.dev.tvmania.featuretvshow.data.local.database.TvManiaDatabase
import com.dev.tvmania.featuretvshow.data.remote.api.Api
import com.dev.tvmania.featuretvshow.data.repository.TvShowRepositoryImpl
import com.dev.tvmania.featuretvshow.domain.repository.TvShowRepository
import com.dev.tvmania.featuretvshow.domain.usecase.*
import com.dev.tvmania.util.ApiBuilder
import com.dev.tvmania.util.TV_MANIA_DATABASE
import com.google.gson.Gson
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
    fun providesTvShowRepository(api: Api, database: TvManiaDatabase): TvShowRepository{
        return TvShowRepositoryImpl(api = api, database = database)
    }

    @Singleton
    @Provides
    fun providesTvShowUseCases(repository: TvShowRepository): TvShowUseCases{
        return TvShowUseCases(
            getTvShows = GetTvShows(repository = repository),
            getTvShowDetail = GetTvShowDetail(repository = repository),
            getCarouselImages = GetCarouselImages(repository= repository),
            getCachedTvShowDetail = GetCachedTvShowDetail(repository = repository),
            addOrRemoveTvShowBookmark = AddOrRemoveTvShowBookmark(repository = repository),
            inBookmarks = InBookmarks(repository = repository)
        )
    }

    @Singleton
    @Provides
    fun providesTvManiaDatabase(application: Application): TvManiaDatabase{
        return Room.databaseBuilder(
            application,
            TvManiaDatabase::class.java,
            TV_MANIA_DATABASE
        ).addTypeConverter(ControlledConverter(Gson()))
            .fallbackToDestructiveMigration()
            .build()
    }
}