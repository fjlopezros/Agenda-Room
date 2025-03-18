package com.fjlr.room_practica

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactoDao {
    @Query("SELECT * FROM contactos")
    fun getAll(): List<ContactoEntity>

    @Query("SELECT * FROM contactos WHERE nombre LIKE :nombre")
    fun findByName(nombre: String): ContactoEntity?

    @Query("SELECT * FROM contactos WHERE telefono = :telefono")
    fun findByTelefono(telefono: Int): ContactoEntity?

    @Insert
    fun insertAll(vararg contactos: ContactoEntity)

    @Delete
    fun delete(contacto: ContactoEntity)

    @Update
    fun update(contacto: ContactoEntity)
}
