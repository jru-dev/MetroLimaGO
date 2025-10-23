package com.proyecto.myapplication.screens.estaciones

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.proyecto.myapplication.MainActivity
import com.proyecto.myapplication.R
import com.proyecto.myapplication.databinding.ActivityEstacionesBinding
import kotlinx.coroutines.launch

class EstacionesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstacionesBinding
    private lateinit var adapter: EstacionesAdapter

    private val viewModel: EstacionesViewModel by viewModels {
        EstacionesViewModelFactory(
            (application as MainActivity).estacionRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSearchView()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        adapter = EstacionesAdapter { estacion ->
            // Por ahora solo mostramos un Toast
            // EstacionDetalleActivity se creará en el Día 3
            Toast.makeText(
                this,
                "Estación: ${estacion.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvEstaciones.apply {
            this.adapter = this@EstacionesActivity.adapter
            layoutManager = LinearLayoutManager(this@EstacionesActivity)
            setHasFixedSize(true)
        }
    }

    private fun setupSearchView() {
        binding.etBuscar.doOnTextChanged { text, _, _, _ ->
            val query = text?.toString() ?: ""
            viewModel.buscarEstaciones(query)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.estaciones.collect { estaciones ->
                adapter.submitList(estaciones)
                updateEmptyState(estaciones.isEmpty())
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.totalEstaciones.collect { total ->
                binding.tvTotalEstaciones.text = getString(R.string.total_estaciones, total)
            }
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty && !viewModel.isLoading.value) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvEstaciones.visibility = View.GONE
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvEstaciones.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}