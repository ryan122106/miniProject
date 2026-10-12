package com.example.mini_project.data.repo

import com.example.mini_project.data.model.Note

interface NoteRepo {

    suspend fun getNotes(): List<Note>

    suspend fun getNoteDetails(noteId: String): Note

    suspend fun addNote(
        title: String,
        content: String,
        colour: String
    ): String

    suspend fun updateNote(
        noteId: String,
        title: String,
        content: String,
        colour: String
    )

    suspend fun moveToTrash(noteId: String)

    suspend fun getTrashNotes(): List<Note>

    suspend fun restoreNote(noteId: String)

    suspend fun deleteNote(noteId: String)

    suspend fun emptyTrash()

    suspend fun deleteExpiredNotes()
}