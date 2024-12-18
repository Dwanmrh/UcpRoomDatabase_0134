package com.dwan.ucp_2.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dwan.ucp_2.data.entity.Dosen
import com.dwan.ucp_2.ui.customwidget.AppBar
import com.dwan.ucp_2.ui.viewmodel.DosenViewModel
import com.dwan.ucp_2.ui.viewmodel.PenyediaViewModel

@Composable
fun DosenView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    onAddDosen:() -> Unit,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Daftar Dosen",
                showBackButton = true,
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen"
                )
            }
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cardDosen(
    dsn: Dosen,
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
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = dsn.nama,
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
                    text = dsn.nidn,
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
                    text = dsn.jenisKelamin,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}