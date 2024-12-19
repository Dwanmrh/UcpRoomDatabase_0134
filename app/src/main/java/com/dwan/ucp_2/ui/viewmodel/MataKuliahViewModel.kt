package com.dwan.ucp_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryDosen
import com.dwan.ucp_2.repository.RepositoryMataKuliah

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah) : ViewModel() {

}

data class FormErrorStateMk(
    val kodeMk: String? = null,
    val namaMk: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPu: String? = null
) {
    fun isValid(): Boolean {
        return kodeMk == null && namaMk == null && sks == null && semester == null
                && jenis == null && dosenPu == null
    }
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