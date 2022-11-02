package com.dev.tvmania.featuretvshow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dev.tvmania.featuretvshow.data.local.dao.BookmarkDao
import com.dev.tvmania.featuretvshow.data.local.dao.PersonDao
import com.dev.tvmania.featuretvshow.data.local.dao.TvShowDao
import com.dev.tvmania.featuretvshow.data.local.entity.*

@Database(
    version = 2,
    entities = [
        TvShowEntity::class,
        TvShowDetailEntity::class,
        PersonEntity::class,
        TvShowPersonCrossRef::class,
        TvShowRemoteKeysEntity::class,
        BookmarkEntity::class
    ],
    exportSchema = false
)
@TypeConverters(ControlledConverter::class)
abstract class TvManiaDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao

    abstract fun tvShowDao(): TvShowDao

    abstract fun bookmarkDao(): BookmarkDao

}