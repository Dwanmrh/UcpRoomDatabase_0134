package com.dwan.ucp_2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.repository.RepositoryDosen
import com.dwan.ucp_2.repository.RepositoryMataKuliah
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repositoryDosen: RepositoryDosen,
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                // Mengumpulkan data dari Flow
                val dosenList = repositoryDosen.getAllDosen().first() // Mengambil data pertama dari Flow
                val mataKuliahList = repositoryMataKuliah.getAllMk().first() // Sama untuk Mata Kuliah

                // Memperbarui state UI
                _uiState.value = _uiState.value.copy(
                    dosenList = dosenList,
                    mataKuliahList = mataKuliahList,
                    isLoading = false
                )
            } catch (e: Exception) {
                // Menangani kesalahan
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Terjadi kesalahan saat memuat data"
                )
            }
        }
    }
}

data class HomeUiState(
    val dosenList: List<Dosen> = emptyList(),
    val mataKuliahList: List<MataKuliah> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
