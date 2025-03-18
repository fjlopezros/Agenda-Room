package com.fjlr.room_practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.room_practica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: AppDataBase
    private lateinit var contactoDao: ContactoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar la base de datos y DAO
        db = DatabaseSingleton.getDatabase(this)
        contactoDao = db.contactoDao()

        // Inicializar RecyclerView después de setContentView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        addContacto()
    }

    override fun onResume() {
        super.onResume()

        // Cargar la lista de contactos desde la base de datos en un hilo de fondo
        Thread {
            val listaDeContactos = contactoDao.getAll()  // Recuperar los contactos
            runOnUiThread {
                // Asignar el adaptador con la lista actualizada de contactos
                recyclerView.adapter = ContactoAdapter(listaDeContactos)
            }
        }.start()
    }

    private fun addContacto() {
        binding.btAdd.setOnClickListener {
            val intent = Intent(this, AddContactoActivity::class.java)
            startActivity(intent)
        }
    }
}
