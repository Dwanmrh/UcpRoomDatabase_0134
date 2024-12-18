package com.dwan.ucp_2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.repository.RepositoryDosen

class DosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {

}

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

data class DosenEvent(
    val nidn: String,
    val nama: String,
    val jenisKelamin: String
)