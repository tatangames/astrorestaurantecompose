package com.tatanstudios.astropollococina.extras

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension function to create a DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenManager(private val context: Context) {

    // Clave para almacenar el id del usuario
    private val ID_KEY = stringPreferencesKey("ID")


    // Guardar el id
    suspend fun saveID(id: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }

    // Obtener id desde DataStore
    val idUsuario: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[ID_KEY] ?: ""
        }

    // Borrar token (Cierre de sesión)
    suspend fun deletePreferences() {
        context.dataStore.edit { preferences ->
            preferences.remove(ID_KEY)
        }
    }
}