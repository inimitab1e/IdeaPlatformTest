package com.inimitable.ideaplatformtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: Int,
    val tags: List<String>,
    val amount: Int
)