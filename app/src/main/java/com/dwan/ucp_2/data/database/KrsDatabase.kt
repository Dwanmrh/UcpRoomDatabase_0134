package com.dwan.ucp_2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dwan.ucp_2.data.dao.DosenDao
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.data.entity.MataKuliah

@Database(entities = [Dosen::class], [MataKuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase: RoomDatabase() {
}