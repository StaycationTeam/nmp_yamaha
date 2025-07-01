package com.yamaha.healingyuk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response
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
                val queue = Volley.newRequestQueue(this)
                val url = "https://ubaya.xyz/native/160422022/add_location.php" // ganti dengan path kamu

                val stringRequest = object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        Toast.makeText(this, "Lokasi berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["name"] = name
                        params["category"] = category
                        params["image_url"] = photoUrl
                        params["short_description"] = shortDesc
                        params["long_description"] = description
                        return params
                    }
                }
                queue.add(stringRequest)
            }
        }

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
