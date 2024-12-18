package com.dwan.ucp_2.repository

import com.dwan.ucp_2.data.dao.MataKuliahDao
import com.dwan.ucp_2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMataKuliah(
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMataKuliah {

    override suspend fun insertMk(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMk(mataKuliah)
    }

    override fun getAllMk(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMk()
    }

    override fun getMk(kodeMk: String): Flow<MataKuliah> {
        return mataKuliahDao.getMk(kodeMk)
    }

    override suspend fun deleteMk(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMk(mataKuliah)
    }

    override suspend fun updateMk(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMk(mataKuliah)
    }
}