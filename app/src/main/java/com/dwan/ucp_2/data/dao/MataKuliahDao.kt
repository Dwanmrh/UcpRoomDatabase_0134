package com.dwan.ucp_2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dwan.ucp_2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {

    // get all data
    @Query("SELECT * FROM matakuliah")
    fun getAllMk(): Flow<List<MataKuliah>>

    // get Mk
    @Query("SELECT * FROM matakuliah WHERE kodeMk = :kodeMk")
    fun getMk(kodeMk: String): Flow<MataKuliah>

    // delete Mk
    @Delete
    suspend fun deleteMk(mataKuliah: MataKuliah)

    // update Mk
    @Update
    suspend fun updateMk(mataKuliah: MataKuliah)

    @Insert
    suspend fun insertMk(mataKuliah: MataKuliah)
}