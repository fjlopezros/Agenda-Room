package com.fjlr.room_practica.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactoDao {
    @Query("SELECT * FROM contactos")
    fun getAll(): List<ContactoEntity>

    @Query("SELECT * FROM contactos WHERE id = :contactoId")
    fun getContactoById(contactoId: Int): ContactoEntity

    @Query("SELECT * FROM contactos WHERE nombre LIKE :nombre")
    fun findByName(nombre: String): ContactoEntity?

    @Query("SELECT * FROM contactos WHERE telefono = :telefono")
    fun findByTelefono(telefono: Int): ContactoEntity?

    @Insert
    fun insertAll(vararg contactos: ContactoEntity)

    @Delete
    fun delete(contacto: ContactoEntity)

    @Query("DELETE FROM contactos WHERE id = :contactoId")
    fun deleteContactoById(contactoId: Int)

    @Update
    fun update(contacto: ContactoEntity)
}
