package com.dwan.ucp_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryDosen
import com.dwan.ucp_2.repository.RepositoryMataKuliah

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah) : ViewModel() {

}

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kodeMk = kodeMk,
    namaMk = namaMk,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPu = dosenPu
)

data class MataKuliahEvent(
    val kodeMk: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPu: String = ""
)