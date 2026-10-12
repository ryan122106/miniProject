package com.example.mini_project.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    protected suspend fun <T> safeApiCall(
        func: suspend () -> T
    ): T? {
        return try {
            func()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _error.emit(
                e.localizedMessage ?: "Something went wrong. Please try again."
            )
            null
        }
    }
}