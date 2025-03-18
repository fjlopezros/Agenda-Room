package com.fjlr.room_practica

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactoEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun contactoDao(): ContactoDao
}