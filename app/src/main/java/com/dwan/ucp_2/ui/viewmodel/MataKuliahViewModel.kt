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

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah) : ViewModel() {

    var uiState by mutableStateOf(MkUiState())
    val MkUiState: StateFlow<MkUiState> = repositoryMataKuliah.getAllMk()
        .filterNotNull()
        .map {
            MkUiState(
                listDsn = it.toList(),
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
}

data class MkUiState(
    val listDsn:List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val mataKuliahEvent: MataKuliahEvent,
    val isEntryValid: FormErrorState = FormErrorState(),
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