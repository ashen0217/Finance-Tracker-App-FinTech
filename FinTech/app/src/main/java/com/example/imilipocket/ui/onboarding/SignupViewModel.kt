package com.example.imilipocket.ui.onboarding

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imilipocket.data.repository.AuthRepository

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _signupState = MutableLiveData<SignupState>()
    val signupState: LiveData<SignupState> = _signupState

    fun signUp(username: String, password: String) {
        when {
            username.length < 3 -> {
                _signupState.value = SignupState.Error("Username must be at least 3 characters long")
            }
            password.length < 6 -> {
                _signupState.value = SignupState.Error("Password must be at least 6 characters long")
            }
            !password.any { it.isDigit() } -> {
                _signupState.value = SignupState.Error("Password must contain at least one number")
            }
            !password.any { it.isUpperCase() } -> {
                _signupState.value = SignupState.Error("Password must contain at least one uppercase letter")
            }
            else -> {
                if (authRepository.signUp(username, password)) {
                    _signupState.value = SignupState.Success
                } else {
                    _signupState.value = SignupState.Error("Username already taken")
                }
            }
        }
    }
}

sealed class SignupState {
    object Success : SignupState()
    data class Error(val message: String) : SignupState()
}

class SignupViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(AuthRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 