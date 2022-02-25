package com.edlo.mydemoapp.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [PavilionData::class, PlantData::class], version = 1, exportSchema = false)
abstract class TaipeiZooDB : RoomDatabase() {
    companion object {
        val DB_NAME = "db_github_user"
        private var INSTANCE: TaipeiZooDB? = null

//        private val MIGRATION_1_2 = object: Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // TODO
//            }
//        }

        fun getDatabase(@ApplicationContext context: Context): TaipeiZooDB {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder( context,
                    TaipeiZooDB::class.java, DB_NAME )
//                .addMigrations(MIGRATION_1_2)
                .build()
            }
            return INSTANCE!!
        }
    }
    abstract fun taipeiZooDao(): TaipeiZooDao
}