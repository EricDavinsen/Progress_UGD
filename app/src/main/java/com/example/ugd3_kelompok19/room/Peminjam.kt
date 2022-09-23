package com.example.ugd3_kelompok19.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Peminjam (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val note: String
)