// src/main/java/com/example/practica2/MainActivity.kt

package com.example.practica2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        // Configurar el Navigation Drawer
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Establece la pantalla inicial al abrir el menú lateral
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_products -> {
                    // Carga la ProductsActivity
                    val productsIntent = Intent(this, ProductsActivity::class.java)
                    startActivity(productsIntent)
                }
                R.id.nav_cart -> {
                    // Carga la CartActivity
                    val cartIntent = Intent(this, CartActivity::class.java)
                    startActivity(cartIntent)
                }
                R.id.nav_logout -> {
                    // Cierra la sesión y regresa a la pantalla de Login
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}