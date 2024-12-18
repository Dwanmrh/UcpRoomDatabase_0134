package com.dwan.ucp_2.dependenciesinjection

import android.content.Context
import com.dwan.ucp_2.data.database.KrsDatabase
import com.dwan.ucp_2.repository.LocalRepositoryDosen
import com.dwan.ucp_2.repository.LocalRepositoryMataKuliah
import com.dwan.ucp_2.repository.RepositoryDosen
import com.dwan.ucp_2.repository.RepositoryMataKuliah

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMataKuliah: RepositoryMataKuliah
}

class ContainerApp(private val context: Context): InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao())
    }
    override val repositoryMataKuliah: RepositoryMataKuliah by lazy {
        LocalRepositoryMataKuliah(KrsDatabase.getDatabase(context).mataKuliahDao())
    }
}