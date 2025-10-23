package com.proyecto.myapplication.screens.estaciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.myapplication.data.model.Estacion
import com.proyecto.myapplication.databinding.ItemEstacionBinding

class EstacionesAdapter(
    private val onEstacionClick: (Estacion) -> Unit
) : ListAdapter<Estacion, EstacionesAdapter.EstacionViewHolder>(EstacionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionViewHolder {
        val binding = ItemEstacionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EstacionViewHolder(binding, onEstacionClick)
    }

    override fun onBindViewHolder(holder: EstacionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EstacionViewHolder(
        private val binding: ItemEstacionBinding,
        private val onEstacionClick: (Estacion) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(estacion: Estacion) {
            binding.apply {
                tvNombreEstacion.text = estacion.nombre
                tvLineaEstacion.text = estacion.linea
                tvDistrito.text = estacion.distrito

                root.setOnClickListener {
                    onEstacionClick(estacion)
                }
            }
        }
    }

    class EstacionDiffCallback : DiffUtil.ItemCallback<Estacion>() {
        override fun areItemsTheSame(oldItem: Estacion, newItem: Estacion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Estacion, newItem: Estacion): Boolean {
            return oldItem == newItem
        }
    }
}