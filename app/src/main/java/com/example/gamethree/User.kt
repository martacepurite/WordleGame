package com.example.gamethree

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
// spēlētāja konts - glabā lietotājvārdu un iegūtos punktus
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "points") var points : Int
)
