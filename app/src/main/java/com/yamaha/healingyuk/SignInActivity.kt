package com.yamaha.healingyuk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import androidx.core.content.edit

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailInput = findViewById<EditText>(R.id.inputEmail)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegist)

        // Navigasi ke Sign Up Activity
        txtRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Login button click listener
        btnLogin.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tolong isi email dan password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mengirimkan data login ke server menggunakan Volley
            loginToServer(email, password)
        }
    }

    // Fungsi untuk mengirim data login ke server
    private fun loginToServer(email: String, password: String) {
        val url = "https://ubaya.xyz/native/160422022/login.php"  // Ganti dengan URL server kamu yang benar

        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password

        // Membuat permintaan POST menggunakan Volley
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val json = org.json.JSONObject(response)
                    if (json.getString("status") == "success") {
                        val name = json.getString("name")  // Ambil nama dari JSON

                        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("email", email)
                            putString("name", name)
                            putBoolean("is_logged_in", true)
                            apply()
                        }

                        Toast.makeText(this, "Login sukses!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login gagal: ${json.getString("message")}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Format respon tidak valid", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Menangani error
                error.printStackTrace()
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                return params  // Mengirimkan data login ke server
            }
        }

        // Menambahkan request ke dalam request queue
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }
}
