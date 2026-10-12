package com.example.mini_project.data.repo

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepoImpl(
    private val auth: FirebaseAuth
) : AuthRepo {

    override fun isLoggedIn(): Boolean {
        val user = auth.currentUser
        return user != null && !user.isAnonymous
    }

    override suspend fun login(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(
            email.trim(),
            password
        ).await()
    }

    override suspend fun register(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(
            email.trim(),
            password
        ).await()
    }

    override fun logout() {
        auth.signOut()
    }
}