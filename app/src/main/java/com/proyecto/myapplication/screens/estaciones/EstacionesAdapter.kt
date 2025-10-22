package com.proyecto.myapplication.ui.rutas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.myapplication.databinding.ItemEstacionBinding

class EstacionesAdapter : ListAdapter<String, EstacionesAdapter.EstacionViewHolder>(EstacionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionViewHolder {
        val binding = ItemEstacionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EstacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstacionViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }

    class EstacionViewHolder(
        private val binding: ItemEstacionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(estacion: String, numero: Int) {
            binding.tvNumeroEstacion.text = numero.toString()
            binding.tvNombreEstacion.text = estacion
        }
    }

    class EstacionDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}