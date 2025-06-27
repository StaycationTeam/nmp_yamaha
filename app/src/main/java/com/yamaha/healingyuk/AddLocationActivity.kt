package com.yamaha.healingyuk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yamaha.healingyuk.databinding.ActivityAddLocationBinding

class AddLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup spinner
        val categories = listOf(
            "Cafe", "Resto", "Warkop", "Hotel", "Karaoke", "Arcade",
            "Playground", "Billiard", "Bowling", "Bar"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategory.adapter = adapter

        // Button click
        binding.btnAddLocation.setOnClickListener {
            val name = binding.etLocationName.text.toString()
            val category = binding.spinnerCategory.selectedItem.toString()
            val photoUrl = binding.etPhotoUrl.text.toString()
            val shortDesc = binding.etShortDesc.text.toString()
            val description = binding.etDescription.text.toString()

            // Validasi sederhana
            if (name.isBlank() || photoUrl.isBlank()) {
                Toast.makeText(this, "Isi nama dan URL gambar!", Toast.LENGTH_SHORT).show()
            } else {
                // Tambahkan ke database / tampilkan toast / kembali ke fragment sebelumnya
                Toast.makeText(this, "Lokasi '$name' berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                finish() // kembali ke halaman sebelumnya
            }
        }

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
