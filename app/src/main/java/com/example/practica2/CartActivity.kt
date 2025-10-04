package com.example.practica2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupNavigationDrawer(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.rv_cart)
        val emptyCartTextView: TextView = findViewById(R.id.cart_empty_message)
        val fabAlarm: FloatingActionButton = findViewById(R.id.fab_pending_alarm)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val cartProducts = ShoppingCart.getProducts()

        if (cartProducts.isEmpty()) {
            emptyCartTextView.visibility = TextView.VISIBLE
            recyclerView.visibility = RecyclerView.GONE
        } else {
            emptyCartTextView.visibility = TextView.GONE
            recyclerView.visibility = RecyclerView.VISIBLE
            val adapter = CartAdapter(cartProducts)
            recyclerView.adapter = adapter
        }

        fabAlarm.setOnClickListener {
            if (cartProducts.isNotEmpty()) {
                setPendingCartAlarm()
                Toast.makeText(this, "Alarma para carrito pendiente establecida en 1 minuto.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No puedes establecer una alarma sin productos en el carrito.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNavigationDrawer(toolbar: Toolbar) {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_products -> {
                    val intent = Intent(this, ProductsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_cart -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_alarms -> {
                    val intent = Intent(this, AlarmaActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_multimedia -> {
                    val intent = Intent(this, MultimediaActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_camara -> {
                    val intent = Intent(this, CamaraActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_camerax -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_logout -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

    private fun setPendingCartAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, PendingActivity::class.java).apply {
            putExtra("message", "¡Tienes el carrito pendiente!")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val futureInMillis = SystemClock.elapsedRealtime() + 60 * 1000
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis,
            pendingIntent
        )
    }
}