package com.dwan.ucp_2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kodeMk: String,
    val namaMk: String,
    val sks: String,
    val semester: String,
    val jenis: String,
    val dosenPu: String
)
