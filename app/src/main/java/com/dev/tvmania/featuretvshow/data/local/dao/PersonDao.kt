package com.dev.tvmania.featuretvshow.data.local.dao

import androidx.room.*
import com.dev.tvmania.featuretvshow.data.local.entity.PersonEntity
import com.dev.tvmania.util.PERSONS_TABLE

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonEntity>)

    @Query("DELETE FROM $PERSONS_TABLE")
    suspend fun deleteAll()
}