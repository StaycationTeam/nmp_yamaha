package com.yamaha.healingyuk

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.yamaha.healingyuk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Ganti findViewById dengan binding
        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set toolbar
        setSupportActionBar(binding.topAppBar)

        // Drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.topAppBar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Menu di drawer kiri
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.changePassword -> {
                    startActivity(Intent(this, ChangePasswordActivity::class.java))
                }
                R.id.logout -> {
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        // Bottom nav bar
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemExplore -> loadFragment(ExploreFragment())
                R.id.itemFavorites -> loadFragment(FavoritesFragment())
                R.id.itemProfile -> loadFragment(ProfileFragment())
            }
            true
        }

        // Load default fragment saat pertama kali
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.itemExplore
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
