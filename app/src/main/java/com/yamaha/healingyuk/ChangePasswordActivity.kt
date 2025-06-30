package com.yamaha.healingyuk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yamaha.healingyuk.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle tombol back di toolbar
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.btnChangePassword.setOnClickListener {
            val oldPass = binding.etOldPassword.text.toString()
            val newPass = binding.etNewPassword.text.toString()
            val confirmPass = binding.etConfirmPassword.text.toString()

            if (oldPass.isBlank() || newPass.isBlank() || confirmPass.isBlank()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmPass) {
                Toast.makeText(this, "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Simpan perubahan password ke database/server
            val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
            val email = sharedPref.getString("email", null)

            if (email != null) {
                changePassword(email, oldPass, newPass)
            } else {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
            finish() // tutup activity
        }
    }

    private fun changePassword(email: String, oldPass: String, newPass: String) {
        val url = "https://ubaya.xyz/native/160422022/change_password.php"

        val params = HashMap<String, String>()
        params["email"] = email
        params["old_password"] = oldPass
        params["new_password"] = newPass

        val request = object : com.android.volley.toolbox.StringRequest(
            Method.POST, url,
            com.android.volley.Response.Listener { response ->
                try {
                    val json = org.json.JSONObject(response)
                    if (json.getString("status") == "success") {
                        Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Format response error", Toast.LENGTH_SHORT).show()
                }
            },
            com.android.volley.Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> = params
        }

        com.android.volley.toolbox.Volley.newRequestQueue(this).add(request)
    }
}
