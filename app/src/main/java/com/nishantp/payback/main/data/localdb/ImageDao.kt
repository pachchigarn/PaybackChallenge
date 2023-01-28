package com.nishantp.payback.main.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("SELECT * FROM images")
    suspend fun getImages(): List<ImageEntity>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getImageById(id: Int): ImageEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(listOfImages: List<ImageEntity>)

    @Query("DELETE FROM images")
    suspend fun nukeTable()
}