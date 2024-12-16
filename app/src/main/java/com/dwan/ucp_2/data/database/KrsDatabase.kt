package com.dwan.ucp_2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dwan.ucp_2.data.dao.DosenDao
import com.dwan.ucp_2.data.dao.MataKuliahDao
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.data.entity.MataKuliah
import kotlin.concurrent.Volatile

@Database(entities = [Dosen::class], [MataKuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase: RoomDatabase() {
    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object {
        @Volatile
        private var Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return(Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    KrsDatabase::class.java,
                    name ="KrsDatabase"
                )
                    .build().also { Instance = it }
            })
        }

    }
}