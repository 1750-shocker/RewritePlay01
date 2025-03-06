package com.wzh.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wzh.model.room.dao.AlmanacDao
import com.wzh.model.room.dao.BannerBeanDao
import com.wzh.model.room.dao.BrowseHistoryDao
import com.wzh.model.room.dao.HotKeyDao
import com.wzh.model.room.dao.ProjectClassifyDao
import com.wzh.model.room.entity.Almanac
import com.wzh.model.room.entity.Article
import com.wzh.model.room.entity.BannerBean
import com.wzh.model.room.entity.HotKey
import com.wzh.model.room.entity.ProjectClassify

@Database(
    entities = [ProjectClassify::class, Article::class, HotKey::class, BannerBean::class, Almanac::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectClassifyDao(): ProjectClassifyDao

    abstract fun browseHistoryDao(): BrowseHistoryDao

    abstract fun hotKeyDao(): HotKeyDao

    abstract fun bannerBeanDao(): BannerBeanDao

    abstract fun almanacDao(): AlmanacDao

    companion object {
        // 单例模式，方式打开多个AppDatabase实例
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}