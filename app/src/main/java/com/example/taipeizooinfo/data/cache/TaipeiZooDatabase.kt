package com.example.taipeizooinfo.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taipeizooinfo.data.cache.model.PlantEntity
import com.example.taipeizooinfo.data.cache.model.RemoteKeyEntity
import com.example.taipeizooinfo.data.cache.model.SectionEntity
import com.example.taipeizooinfo.data.util.DataConstants.DATABASE_NAME

@Database(entities = [SectionEntity::class,PlantEntity::class,RemoteKeyEntity::class],version = 3,exportSchema = false)
abstract class TaipeiZooDatabase:RoomDatabase() {

    abstract fun getSectionDao():SectionDao
    abstract fun getPlantDao():PlantDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao

    companion object{

        @Volatile
        private var INSTANCE:TaipeiZooDatabase?=null

        fun getInstance(context:Context ):TaipeiZooDatabase{
            return INSTANCE?:synchronized(this){
                val instance=Room.databaseBuilder(context,TaipeiZooDatabase::class.java,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                instance
            }
        }

    }
}