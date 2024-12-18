package com.dwan.ucp_2.repository

import com.dwan.ucp_2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMataKuliah {

    suspend fun insertMk(mataKuliah: MataKuliah)

    // getAllMk
    fun getAllMk(): Flow<List<MataKuliah>>

    // getMk
    fun getMk(kodeMk: String): Flow<MataKuliah>

    // deleteMk
    suspend fun deleteMk(mataKuliah: MataKuliah)

    // updateMk
    suspend fun updateMk(mataKuliah: MataKuliah)
}