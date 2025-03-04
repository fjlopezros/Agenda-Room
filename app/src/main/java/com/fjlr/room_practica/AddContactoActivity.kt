package com.fjlr.room_practica

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.room_practica.databinding.ActivityAddContactoBinding

class AddContactoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddContactoBinding

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

        guardar()
        cancelar()

    }
    private fun guardar(){
        binding.btGuardar.setOnClickListener{
            val nombre = binding.etNombre.text.toString()
            val telefono = binding.etTelefono.text.toString()

            if (nombre.isNotEmpty() && telefono.isNotEmpty()) {
                // Aquí guardar info en ROOM
                Toast.makeText(this, "Guardado: $nombre, $telefono", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun cancelar(){
        binding.btCancelar.setOnClickListener {
            finish()
        }
    }
}