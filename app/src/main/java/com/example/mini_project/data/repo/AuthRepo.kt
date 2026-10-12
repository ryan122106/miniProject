package com.example.mini_project.data.repo

import com.example.mini_project.data.firebase.FirebaseClient

interface AuthRepo {

    fun isLoggedIn(): Boolean

    suspend fun login(
        email: String,
        password: String
    )

    suspend fun register(
        email: String,
        password: String
    )

    fun logout()

    companion object {
        fun getInstance(): AuthRepo {
            return AuthRepoImpl(FirebaseClient.auth)
        }
    }
}