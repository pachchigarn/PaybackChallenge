package com.nishantp.payback.main.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nishantp.payback.constants.AppConstants

@Database(
    entities = [ImageEntity::class], version = AppConstants.DATABASE_VERSION
)
abstract class PaybackDatabase : RoomDatabase() {

    abstract val imageDao: ImageDao

}