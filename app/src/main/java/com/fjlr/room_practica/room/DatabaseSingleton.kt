package com.fjlr.room_practica.room

import android.content.Context
import androidx.room.Room

object DatabaseSingleton {

    // Declarar la base de datos
    private var INSTANCE: AppDataBase? = null

    // Obtener la instancia de la base de datos (si no existe, se crea)
    fun getDatabase(context: Context): AppDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "contactos_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
