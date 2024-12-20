package com.dwan.ucp_2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.view.FormMK
import com.dwan.ucp_2.ui.viewmodel.DosenViewModel
import com.dwan.ucp_2.ui.viewmodel.PenyediaViewModel
import com.dwan.ucp_2.ui.viewmodel.UpdateMataKuliahViewModel
import kotlinx.coroutines.launch

@Composable
fun UpdateMataKuliahView(
    onBack: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    dosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.updateUiState
    val dosenUiState by dosenViewModel.dosenUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var selectedDosen by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(dosenUiState.listDsn) {
        // Set default value jika daftar dosen tersedia
        if (dosenUiState.listDsn.isNotEmpty()) {
            selectedDosen = dosenUiState.listDsn.first().nama
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppBar(
                title = "Edit Mata Kuliah",
                showBackButton = true,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Form Input
            FormMK(
                mataKuliahEvent = uiState.mataKuliahEvent,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onNavigateMataKuliah = { },
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFieldsMk()) {
                            viewModel.updateData(dosenPembimbing = selectedDosen)
                            onNavigateMataKuliah()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}