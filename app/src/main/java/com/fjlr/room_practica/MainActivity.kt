package com.fjlr.room_practica

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.room_practica.adapter.ContactoAdapter
import com.fjlr.room_practica.databinding.ActivityMainBinding
import com.fjlr.room_practica.room.AppDataBase
import com.fjlr.room_practica.room.ContactoDao
import com.fjlr.room_practica.room.DatabaseSingleton

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


        init()
        addContacto()

    }

    override fun onResume() {
        super.onResume()
        Thread {
            val listaDeContactos = contactoDao.getAll()
            runOnUiThread {
                recyclerView.adapter = ContactoAdapter(listaDeContactos) { contacto ->
                    val intent = Intent(this, ContactoDetailActivity::class.java)
                    intent.putExtra("CONTACTO_ID", contacto.id)
                    startActivity(intent)
                }
            }
        }.start()
    }


    private fun addContacto() {
        binding.btAdd.setOnClickListener {
            val intent = Intent(this, AddContactoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {
        db = DatabaseSingleton.getDatabase(this)
        contactoDao = db.contactoDao()

        // Inicializar RecyclerView después de setContentView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
