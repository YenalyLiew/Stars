package com.yenaly.stars.logic.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yenaly.stars.logic.model.Universe
import com.yenaly.yenaly_libs.utils.applicationContext

/**
 * @ProjectName : Stars
 * @Author : Yenaly Liew
 * @Time : 2022/04/30 030 18:34
 * @Description : Description...
 */
@Database(entities = [Universe::class], version = 1, exportSchema = false)
abstract class UniverseDatabase : RoomDatabase() {

    abstract fun universeDao(): UniverseDao

    companion object {
        private const val DB_NAME = "uni_db"

        @Volatile
        private var INSTANCE: UniverseDatabase? = null

        @JvmStatic
        fun getDatabase(): UniverseDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(applicationContext, UniverseDatabase::class.java, DB_NAME)
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}