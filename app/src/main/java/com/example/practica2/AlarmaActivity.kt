package com.example.practica2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class AlarmaActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmMgr: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarma)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        setupNavigationDrawer()

        val btnAlarma1: Button = findViewById(R.id.btnAlarmal)
        val btnAlarma2: Button = findViewById(R.id.btnAlarma2)
        val btnAlarma3: Button = findViewById(R.id.btnAlarma3)
        val btnAlarma4: Button = findViewById(R.id.btnAlarma4)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)

        val intent = Intent(this, PendingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmMgr = (applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager)

        btnAlarma1.setOnClickListener { alarmaTiempoRealTranscurrido1() }
        btnAlarma2.setOnClickListener { alarmaTiempoRealTranscurrido2() }
        btnAlarma3.setOnClickListener { alarmaRelojEnTiempoReal1() }
        btnAlarma4.setOnClickListener { alarmaRelojEnTiempoReal2() }
        btnCancelar.setOnClickListener { cancelarAlarma() }
    }

    private fun setupNavigationDrawer() {
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

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_products -> {
                    val intent = Intent(this, ProductsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_alarms -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
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
                    val intent = Intent(this, CamaraActivity::class.java)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun alarmaTiempoRealTranscurrido1() {
        val futureInMillis = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES
        val interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES
        alarmMgr.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis,
            interval,
            pendingIntent
        )
        Toast.makeText(applicationContext, "Alarma repetitiva TRT lanzada.", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaTiempoRealTranscurrido2() {
        val futureInMillis = SystemClock.elapsedRealtime() + 60 * 1000
        alarmMgr.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            futureInMillis,
            pendingIntent
        )
        Toast.makeText(applicationContext, "Alarma no repetitiva TRT.", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaRelojEnTiempoReal1() {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 45)
        }
        alarmMgr.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(applicationContext, "Alarma RTR a una hora especifica.", Toast.LENGTH_SHORT).show()
    }

    private fun alarmaRelojEnTiempoReal2() {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 34)
        }
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60 * 1,
            pendingIntent
        )
        Toast.makeText(applicationContext, "Alarma RTR cada minuto.", Toast.LENGTH_SHORT).show()
    }

    private fun cancelarAlarma() {
        alarmMgr.cancel(pendingIntent)
        Toast.makeText(applicationContext, "Alarma cancelada.", Toast.LENGTH_SHORT).show()
    }
}