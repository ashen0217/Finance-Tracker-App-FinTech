package com.example.imilipocket.data.repository

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.MessageDigest
import java.util.Base64

class AuthRepository(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPrefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun signUp(username: String, password: String): Boolean {
        if (isUsernameTaken(username)) {
            return false
        }
        
        val hashedPassword = hashPassword(password)
        encryptedSharedPrefs.edit().apply {
            putString("USERNAME_$username", username)
            putString("PASSWORD_$username", hashedPassword)
            apply()
        }
        return true
    }

    fun login(username: String, password: String): Boolean {
        val storedUsername = encryptedSharedPrefs.getString("USERNAME_$username", null)
        val storedPassword = encryptedSharedPrefs.getString("PASSWORD_$username", null)
        
        return if (storedUsername == username && storedPassword == hashPassword(password)) {
            // Store login state
            encryptedSharedPrefs.edit().putBoolean("IS_LOGGED_IN", true).apply()
            true
        } else {
            false
        }
    }

    fun isLoggedIn(): Boolean {
        return encryptedSharedPrefs.getBoolean("IS_LOGGED_IN", false)
    }

    fun logout() {
        encryptedSharedPrefs.edit().putBoolean("IS_LOGGED_IN", false).apply()
    }

    private fun isUsernameTaken(username: String): Boolean {
        return encryptedSharedPrefs.contains("USERNAME_$username")
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hash)
    }
} 