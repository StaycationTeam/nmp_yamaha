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

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailInput = findViewById<EditText>(R.id.inputEmail)
        val nameInput = findViewById<EditText>(R.id.inputNama)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)
        val rePasswordInput = findViewById<EditText>(R.id.inputRePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        txtLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

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

            // Kalau semua valid, lanjut
            Toast.makeText(this, "Registrasi sukses!", Toast.LENGTH_SHORT).show()
            // Misalnya lanjut ke halaman login
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}