package com.dwan.ucp_2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.viewmodel.DetailMataKuliahViewModel
import com.dwan.ucp_2.ui.viewmodel.DetailUiState
import com.dwan.ucp_2.ui.viewmodel.PenyediaViewModel
import com.dwan.ucp_2.ui.viewmodel.toMataKuliahEntity

@Composable
fun DetailMataKuliahView(
    modifier: Modifier = Modifier,
    viewModel: DetailMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { }, // Callback ke halaman update
    onDeleteClick: () -> Unit = { },
    kodeMk: String
) {
    val detailUiState by viewModel.detailUiState.collectAsState() // Observasi state UI dari ViewModel

    Scaffold(
        topBar = {
            AppBar(
                title = "Detail Mata Kuliah",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    detailUiState.detailUiEvent?.kodeMk?.let(onEditClick) // Panggil navigasi ke update
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mata Kuliah"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailMk(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState, // Mengoper state ke komponen body
            onDeleteClick = {
                viewModel.deleteMk()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMk(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Menampilkan loading spinner
            }
        }

        detailUiState.detailUiEvent != null -> { // Jika data mahasiswa tersedia
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMk(
                    // Konversi UI state ke entity mahasiswa
                    mataKuliah = detailUiState.detailUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
            }
        }

        else -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false // Menutup dialog tanpa hapus
            }
        )
    }
}

@Composable
fun ItemDetailMk(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMk(judul = "Kode MK", data = mataKuliah.kodeMk)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Nama MK", data = mataKuliah.namaMk)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "SKS", data = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Semester", data = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Jenis", data = mataKuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Dospem", data = mataKuliah.dosenPu)
        }
    }
}

@Composable
fun ComponentDetailMk(
    modifier: Modifier = Modifier,
    judul: String,
    data: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = data, fontSize = 20.sp, // Isi data
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ }, // Tidak ada aksi saat dialog ditutup
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) { // Tombol batal
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) { // Tombol konfirmasi
                Text(text = "Yes")
            }
        }
    )
}