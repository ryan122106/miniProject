package com.example.mini_project.data.model

data class Note(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val colour: String = "blue",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L,
    val deletedAt: Long? = null
)