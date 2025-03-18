package com.fjlr.room_practica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.room_practica.databinding.ContactosItemBinding

class ContactoAdapter(private val contactos: List<ContactoEntity>) :
    RecyclerView.Adapter<ContactoAdapter.ContactoAdapterViewHolder>() {

    inner class ContactoAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ContactosItemBinding.bind(view)

        fun bind(contacto: ContactoEntity) {
            binding.tvNombreContacto.text = contacto.nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoAdapterViewHolder =
        ContactoAdapterViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.contactos_item, parent, false)
        )


    override fun onBindViewHolder(holder: ContactoAdapterViewHolder, position: Int) =
        holder.bind(contactos[position])


    override fun getItemCount(): Int = contactos.size
}