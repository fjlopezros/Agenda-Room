package com.fjlr.room_practica

import android.os.Bundle
import android.widget.Toast
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

        init()

        contactoId = intent.getIntExtra("CONTACTO_ID", -1)
        if (contactoId != -1) {
            binding.etNombre.setText(intent.getStringExtra("CONTACTO_NOMBRE"))
            binding.etTelefono.setText(intent.getIntExtra("CONTACTO_TELEFONO", 0).toString())
            binding.btGuardar.text = "Actualizar" // Cambiar el texto del botón
        }

        binding.btGuardar.setOnClickListener { guardarContacto() }
        cancelar()

    }

    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString().trim()
        val telefonoString = binding.etTelefono.text.toString().trim()

        if (nombre.isEmpty() || telefonoString.isEmpty()) {
            Toast.makeText(this, "Faltan datos: Nombre o teléfono vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val telefono = telefonoString.toIntOrNull()
        if (telefono == null) {
            Toast.makeText(this, "Teléfono inválido", Toast.LENGTH_SHORT).show()
            return
        }

        Thread {
            if (contactoId == -1) {
                contactoDao.insertAll(ContactoEntity(nombre = nombre, telefono = telefono))
            } else {
                contactoDao.update(ContactoEntity(id = contactoId, nombre = nombre, telefono = telefono))
            }
            runOnUiThread {
                finish()
            }
        }.start()
    }


    private fun init(){
        db = DatabaseSingleton.getDatabase(this)
        contactoDao = db.contactoDao()
    }

    private fun cancelar(){
        binding.btCancelar.setOnClickListener {
            finish()
        }
    }
}