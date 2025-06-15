package com.yamaha.healingyuk

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Menyesuaikan padding untuk sistem bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi input fields
        val emailInput = findViewById<EditText>(R.id.inputEmail)
        val nameInput = findViewById<EditText>(R.id.inputNama)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val rePasswordInput = findViewById<EditText>(R.id.inputRePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        // Navigasi ke Sign In Activity
        txtLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // Tombol Register klik listener
        btnRegister.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val name = nameInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val rePassword = rePasswordInput.text.toString()

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(this, "Semua form harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != rePassword) {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mengirimkan data Sign Up ke server menggunakan Volley
            signUpToServer(email, name, password)
        }
    }

    // Fungsi untuk mengirim data Sign Up ke server
    private fun signUpToServer(email: String, name: String, password: String) {
        val url = "http://10.0.2.2/Webprog/signup.php"  // Ganti dengan URL server kamu

        val params = HashMap<String, String>()
        params["email"] = email
        params["name"] = name
        params["password"] = password

        // Membuat permintaan POST menggunakan Volley
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Menangani respons dari server
                if (response.contains("success")) {
                    Toast.makeText(this, "Registrasi sukses!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java))  // Arahkan ke Sign In Activity
                    finish()
                } else {
                    Toast.makeText(this, "Registrasi gagal: $response", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Menangani error
                error.printStackTrace()
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                return params  // Mengirimkan data ke server
            }
        }

        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }
}
