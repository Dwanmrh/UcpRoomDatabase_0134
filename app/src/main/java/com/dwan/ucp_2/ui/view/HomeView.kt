package com.dwan.ucp_2.ui.view

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.viewmodel.DosenViewModel

@Composable
fun HomeView(
    onNavigateDosen: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Halaman Utama",
                showBackButton = false,
                onBack ={ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardDosen(
                title = "Daftar Dosen",
                onClick = onNavigateDosen,
                modifier = Modifier.fillMaxWidth()
            )
            CardMk(
                title = "Daftar Mata Kuliah",
                onClick = onNavigateMataKuliah,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDosen(
   title: String,
   onClick: () -> Unit,
   modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(7.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CardMk(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(7.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
        }
    }
}