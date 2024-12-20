package com.dwan.ucp_2.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryMataKuliah
import com.dwan.ucp_2.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel() {

    private val _kodeMk: String = checkNotNull(savedStateHandle[DestinasiDetail.kodeMk])

    val detailUiState: StateFlow<DetailUiState> = repositoryMataKuliah.getMk(_kodeMk)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue =
            DetailUiState(isLoading = true)
        )

    fun deleteMk() {
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMataKuliah.deleteMk(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}

fun MataKuliah.toDetailUiEvent(): MataKuliahEvent {
    return MataKuliahEvent(
        kodeMk = kodeMk,
        namaMk = namaMk,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPu = dosenPu
    )
}