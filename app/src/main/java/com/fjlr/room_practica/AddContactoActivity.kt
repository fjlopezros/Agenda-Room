package com.fjlr.room_practica

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.room_practica.databinding.ActivityAddContactoBinding
import com.fjlr.room_practica.room.AppDataBase
import com.fjlr.room_practica.room.ContactoDao
import com.fjlr.room_practica.room.ContactoEntity
import com.fjlr.room_practica.room.DatabaseSingleton

class AddContactoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactoBinding
    private lateinit var db: AppDataBase
    private lateinit var contactoDao: ContactoDao
    private var contactoId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DatabaseSingleton.getDatabase(this)
        contactoDao = db.contactoDao()

        // Comprobar si se está editando un contacto
        contactoId = intent.getIntExtra("CONTACTO_ID", -1)
        if (contactoId != -1) {
            binding.etNombre.setText(intent.getStringExtra("CONTACTO_NOMBRE"))
            binding.etTelefono.setText(intent.getStringExtra("CONTACTO_TELEFONO").toString())
            binding.btGuardar.text = "Actualizar" // Cambiar el texto del botón
        }

        guardarContacto()
        cancelar()

    }
    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString()
        val telefonoString = binding.etTelefono.text.toString()

        if (nombre.isNotEmpty() && telefonoString.isNotEmpty()) {
            val telefono = telefonoString.toIntOrNull() ?: 0

            Thread {
                if (contactoId == -1) {
                    // Si no hay ID, es un contacto nuevo
                    contactoDao.insertAll(ContactoEntity(nombre = nombre, telefono = telefono))
                } else {
                    // Si hay ID, actualizar el contacto existente
                    contactoDao.update(ContactoEntity(id = contactoId, nombre = nombre, telefono = telefono))
                }
                runOnUiThread { finish() }
            }.start()
        }
    }

    private fun cancelar(){
        binding.btCancelar.setOnClickListener {
            finish()
        }
    }
}