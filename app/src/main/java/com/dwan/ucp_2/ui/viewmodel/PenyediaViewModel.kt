package com.dwan.ucp_2.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dwan.ucp_2.KrsApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                repositoryDosen = KrsApp().containerApp.repositoryDosen
            )
        }
        initializer {
            MataKuliahViewModel(
                repositoryMataKuliah = KrsApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            HomeViewModel(
                repositoryDosen = KrsApp().containerApp.repositoryDosen,
                repositoryMataKuliah = KrsApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailMataKuliahViewModel(
                repositoryMataKuliah = KrsApp().containerApp.repositoryMataKuliah,
                savedStateHandle = savedStateHandle
            )
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            UpdateMataKuliahViewModel(
                savedStateHandle = savedStateHandle,
                repositoryMataKuliah = KrsApp().containerApp.repositoryMataKuliah
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
