package com.dwan.ucp_2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dwan.ucp_2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {

    // get all data
    @Query("SELECT * FROM dosen")
    fun getAllDosen(): Flow<List<Dosen>>

    // get dosen
    @Query("SELECT * FROM dosen WHERE nidn = :nidn")
    fun getDosen(nidn: String): Flow<Dosen>

    // create dosen
    @Insert
    suspend fun insertDosen(dosen: Dosen)
}