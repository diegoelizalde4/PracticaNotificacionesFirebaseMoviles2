package com.example.practica2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class ProductsActivity : AppCompatActivity() {

    private val CHANNEL_ID = "products_channel"
    private var notificationId = 0
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupNavigationDrawer(toolbar)
        createNotificationChannel()

        val recyclerView: RecyclerView = findViewById(R.id.rv_products)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val productList = listOf(
            Product(1, "Laptop", "Laptop de alto rendimiento para gaming.", 1200.00),
            Product(2, "Smartphone", "Teléfono inteligente de última generación.", 800.00),
            Product(3, "Audífonos", "Audífonos con cancelación de ruido.", 150.00)
        )

        val adapter = ProductAdapter(productList) { product ->
            ShoppingCart.addItem(product)
            showProductAddedNotification(product.name)
        }
        recyclerView.adapter = adapter
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
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.nav_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_alarms -> {
                    val intent = Intent(this, AlarmaActivity::class.java)
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Productos"
            val descriptionText = "Notificaciones de productos"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showProductAddedNotification(productName: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.product_added_notification_title))
            .setContentText("$productName ${getString(R.string.product_added_notification_message)}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId++, builder.build())
    }
}