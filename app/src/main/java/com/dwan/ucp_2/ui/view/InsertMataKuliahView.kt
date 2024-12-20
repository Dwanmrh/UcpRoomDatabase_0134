package com.dwan.ucp_2.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.navigation.AlamatNavigasi
import com.dwan.ucp_2.ui.viewmodel.DosenViewModel
import com.dwan.ucp_2.ui.viewmodel.FormErrorState
import com.dwan.ucp_2.ui.viewmodel.FormErrorStateMk
import com.dwan.ucp_2.ui.viewmodel.MataKuliahEvent
import com.dwan.ucp_2.ui.viewmodel.MataKuliahViewModel
import com.dwan.ucp_2.ui.viewmodel.MkUiState
import com.dwan.ucp_2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertMataKuliahView(
    onBack: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    onAddMataKuliah: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState // Ambil UI state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilan Snackbar
                viewModel.resetSnackbarMessageMk()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Tempatkan Snackbar di Scaffold
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            AppBar(
                onBack = onBack,
                showBackButton = true,
                title = "Tambah Mata Kuliah"
            )
            FormMK(
                mataKuliahEvent = uiState.mataKuliahEvent,
                onValueChange = { viewModel.updateState(it) },
                onNavigateMataKuliah = { },
                errorState = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.saveDataMk()
                    onNavigateMataKuliah()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
object DestinasiInsertMk : AlamatNavigasi {
    override val route: String = "insert_mk"
}

@Composable
fun FormMK(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit = {},
    onNavigateMataKuliah: () -> Unit,
    errorState: FormErrorStateMk = FormErrorStateMk(),
    modifier: Modifier = Modifier,
    dosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val dosenUiState by dosenViewModel.dosenUiState.collectAsState()
    val listDosen = dosenUiState.listDsn.map { it.nama }
    val jenis = listOf("Wajib", "Peminatan")

    Column(
        modifier = modifier.fillMaxWidth().padding(20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.namaMk,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(namaMk = it))
            },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.namaMk != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
        )
        Text(
            text = errorState.namaMk ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kodeMk,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(kodeMk = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kodeMk != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
        )
        Text(
            text = errorState.kodeMk ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Mata Kuliah")
        Row(modifier = Modifier.fillMaxWidth())
        {
            jenis.forEach { j ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mataKuliahEvent.jenis == j,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = j))
                        },
                    )
                    Text(text = j,)
                }
            }
        }
        Text(
            text = errorState.jenis ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester") },
        )
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )

        // Replace OutlinedTextField with DropdownMenuField for Dosen Pembimbing
        DropdownMenuField(
            label = "Nama Dosen Pembimbing",
            options = listDosen,
            selectedOption = mataKuliahEvent.dosenPu,
            onOptionSelected = { selectedDosen ->
                onValueChange(mataKuliahEvent.copy(dosenPu = selectedDosen))
            },
            isError = errorState.dosenPu != null,
            errorMessage = errorState.dosenPu
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) } // Mengatur status drop-down
    var currentSelection by remember { mutableStateOf(selectedOption) } // Menyimpan pilihan saat ini

    Column {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {}, // Tidak memungkinkan input manual
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                androidx.compose.material3.IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
