package com.dwan.ucp_2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.data.entity.MataKuliah
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.viewmodel.DetailMataKuliahViewModel
import com.dwan.ucp_2.ui.viewmodel.DosenUiState
import com.dwan.ucp_2.ui.viewmodel.DosenViewModel
import com.dwan.ucp_2.ui.viewmodel.MataKuliahViewModel
import com.dwan.ucp_2.ui.viewmodel.MkUiState
import com.dwan.ucp_2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun MataKuliahView(
    onBack: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    onAddMataKuliah:() -> Unit = { },
    onDetail: (String) -> Unit, // Navigasi ke Detail
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Daftar Mata Kuliah",
                showBackButton = true,
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMataKuliah,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mata Kuliah"
                )
            }
        }
    ) { innerPadding ->
        val mkUiState by viewModel.mkUiState.collectAsState()

        BodyMataKuliahView(
            mkUiState = mkUiState,
            onClick = { kodeMk ->
                onDetail(kodeMk) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyMataKuliahView(
    mkUiState: MkUiState,
    onClick: (String) -> Unit = { }, // Callback untuk kartu
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        mkUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        mkUiState.isError -> {
            LaunchedEffect(mkUiState.errorMessage) {
                mkUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        mkUiState.listMk.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak Ada Data Mata Kuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else ->{
            ListMk(
                listMk = mkUiState.listMk,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMk(
    listMk: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier= modifier
    ) {
        items(
            items = listMk,
            itemContent = { mk ->
                CardMk(
                    mk = mk,
                    onClick = { onClick(mk.kodeMk)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMk(
    mk: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Column(
            modifier = Modifier.padding(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.namaMk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.kodeMk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.sks,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.semester,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.jenis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = mk.dosenPu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}