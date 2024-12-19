package com.dwan.ucp_2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryDosen
import com.dwan.ucp_2.repository.RepositoryMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah) : ViewModel() {

    var uiState by mutableStateOf(MkUiState())
    val mkUiState: StateFlow<MkUiState> = repositoryMataKuliah.getAllMk()
        .filterNotNull()
        .map {
            MkUiState(
                listMk = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(MkUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                MkUiState(
                    isLoading =false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MkUiState(
                isLoading = true,
            )
        )

    private fun validateFieldsMk(): Boolean {
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorStateMk(
            kodeMk = if (event.kodeMk.isNotEmpty()) null else "Kode Mata Kuliah Tidak Boleh Kosong",
            namaMk = if (event.namaMk.isNotEmpty()) null else "Nama Mata Kuliah Tidak Boleh Kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS Mata Kuliah Tidak Boleh Kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Mata Kuliah Tidak Boleh Kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah Tidak Boleh Kosong",
            dosenPu = if (event.dosenPu.isNotEmpty()) null else "Dosen Pembimbing Tidak Boleh Kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveDataMk() {
        val currentEvent = uiState.mataKuliahEvent
        if (validateFieldsMk()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMk(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMk()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input Tidak Valid. Periksa Kembali Data Anda"
            )
        }
    }

    fun resetSnackbarMessageMk() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MkUiState(
    val listMk:List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMk = FormErrorStateMk(),
    val snackBarMessage: String? = null
)

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