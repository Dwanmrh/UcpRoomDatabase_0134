package com.dwan.ucp_2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryMataKuliah
import com.dwan.ucp_2.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel() {

    var updateUiState by mutableStateOf(MkUiState())
        private set

    private val _kodeMk: String = checkNotNull(savedStateHandle[DestinasiUpdate.kodeMk])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMataKuliah.getMk(_kodeMk)
                .filterNotNull()
                .first()
                .toUiStateMk()
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        updateUiState = updateUiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }

    fun validateFieldsMk(): Boolean {
        val event = updateUiState.mataKuliahEvent
        val errorState = FormErrorStateMk(
            kodeMk = if (event.kodeMk.isNotEmpty()) null else "Kode Mata Kuliah Tidak Boleh Kosong",
            namaMk = if (event.namaMk.isNotEmpty()) null else "Nama Mata Kuliah Tidak Boleh Kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS Tidak Boleh Kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Tidak Boleh Kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah Tidak Boleh Kosong",
            dosenPu = if (event.dosenPu.isNotEmpty()) null else "Dosen Pembimbing Tidak Boleh Kosong",
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(
        dosenPembimbing: String
    ) {
        val currentEvent = updateUiState.mataKuliahEvent

        if (validateFieldsMk()) {
            viewModelScope.launch {
                try {
                    // Log untuk memastikan data yang akan diperbarui
                    println("Memperbarui data: $currentEvent")

                    repositoryMataKuliah.updateMk(currentEvent.toMataKuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(), // Reset form
                        isEntryValid = FormErrorStateMk() // Reset error state
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Gagal Diupdate: ${e.message}"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data Tidak Valid. Periksa kembali input Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUiStateMk(): MkUiState = MkUiState(
    mataKuliahEvent = this.toDetailUiEvent()
)
