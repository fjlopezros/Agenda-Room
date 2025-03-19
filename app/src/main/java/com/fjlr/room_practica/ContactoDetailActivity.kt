package com.fjlr.room_practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.room_practica.databinding.ActivityContactoDetailBinding
import com.fjlr.room_practica.room.AppDataBase
import com.fjlr.room_practica.room.ContactoDao
import com.fjlr.room_practica.room.DatabaseSingleton

class ContactoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactoDetailBinding
    private lateinit var db: AppDataBase
    private lateinit var contactoDao: ContactoDao
    private var contactoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityContactoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        eliminar()
        editar()
        volver()

    }

    override fun onResume() {
        super.onResume()
        actualizarDatos()
    }

    private fun actualizarDatos() {
        val contactoId = intent.getIntExtra("CONTACTO_ID", -1)
        if (contactoId != -1) {
            Thread {
                val contacto = contactoDao.getContactoById(contactoId)
                runOnUiThread {
                    binding.tvNombreContactoDetalle.text = contacto.nombre
                    binding.tvTelefonoContactoDetalle.text = contacto.telefono.toString()
                }
            }.start()
        }
    }


    private fun editar(){
        binding.btEditar.setOnClickListener { editarContacto() }
    }

    private fun editarContacto(){
            val intent = Intent(this, AddContactoActivity::class.java)
            intent.putExtra("CONTACTO_ID", contactoId)
            intent.putExtra("CONTACTO_NOMBRE", binding.tvNombreContactoDetalle.text.toString())
            intent.putExtra("CONTACTO_TELEFONO", binding.tvTelefonoContactoDetalle.text.toString().toInt())
        startActivity(intent)
    }

    private fun init(){
        db = DatabaseSingleton.getDatabase(this)
        contactoDao = db.contactoDao()

        contactoId = intent.getIntExtra("CONTACTO_ID", -1)
        if (contactoId != -1) {
            cargarDatos()
        }
    }

    private fun cargarDatos() {
        Thread {
            val contacto = contactoDao.getContactoById(contactoId)
            contacto.let {
                runOnUiThread {
                    binding.tvNombreContactoDetalle.text = it.nombre
                    binding.tvTelefonoContactoDetalle.text = it.telefono.toString()
                }
            }
        }.start()
    }

    private fun eliminar() {
        binding.btEliminar.setOnClickListener { eliminarContacto() }
    }

    private fun eliminarContacto() {
        Thread {
            contactoDao.deleteContactoById(contactoId)
            runOnUiThread {
                finish()
            }
        }.start()
    }

    private fun volver() {
        binding.btVolver.setOnClickListener { finish() }
    }
}