package com.dev.tvmania.featuretvshow.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.tvmania.featuretvshow.domain.model.tvshow.Image
import com.dev.tvmania.featuretvshow.domain.model.tvshowdetail.Person
import com.dev.tvmania.util.PERSONS_TABLE

@Entity(tableName = PERSONS_TABLE)
data class PersonEntity(
   @ColumnInfo(name = "person_id") @PrimaryKey val personId: Long,
   val name: String,
   val url: String,
   val images: List<String>,
   val updated: Long
){
    fun toPerson(): Person{
        return Person(
            id = personId,
            name = name,
            url = url,
            image = if(images.isEmpty()) null else Image(
                medium = images.first(),
                original = images.last()
            ),
            updated = updated
        )
    }
}