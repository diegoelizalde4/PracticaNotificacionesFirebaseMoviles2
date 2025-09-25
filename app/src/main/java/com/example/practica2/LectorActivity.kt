package com.example.practica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.zxing.integration.android.IntentIntegrator

class LectorActivity : AppCompatActivity() {
    private lateinit var codigo: EditText
    private lateinit var descripcion: EditText
    private lateinit var extra1: EditText
    private lateinit var extra2: EditText
    private val products = arrayOfNulls<ProductBC>(10)
    private var productCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lector)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        codigo = findViewById(R.id.edtCodigo)
        descripcion = findViewById(R.id.edtDescripcion)
        extra1 = findViewById(R.id.edtExtra1)
        extra2 = findViewById(R.id.edtExtra2)

        val btnEscanear: Button = findViewById(R.id.btnEscanear)
        val btnCapturar: Button = findViewById(R.id.btnCapturar)
        val btnBuscar: Button = findViewById(R.id.btnBuscar)
        val btnLimpiar: Button = findViewById(R.id.btnLimpiar)

        btnEscanear.setOnClickListener { escanearCodigo() }
        btnCapturar.setOnClickListener { capturarDatos() }
        btnBuscar.setOnClickListener { buscarDatos() }
        btnLimpiar.setOnClickListener { limpiar() }
    }

    private fun escanearCodigo() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        intentIntegrator.setPrompt("Lector de códigos")
        intentIntegrator.setCameraId(0)
        intentIntegrator.setBeepEnabled(true)
        intentIntegrator.setBarcodeImageEnabled(true)
        intentIntegrator.initiateScan()
    }

    private fun capturarDatos() {
        if (codigo.text.toString().isNotEmpty() &&
            descripcion.text.toString().isNotEmpty() &&
            extra1.text.toString().isNotEmpty() &&
            extra2.text.toString().isNotEmpty()
        ) {
            if (productCount < 10) {
                products[productCount] = ProductBC(
                    codigo.text.toString(),
                    descripcion.text.toString(),
                    extra1.text.toString(),
                    extra2.text.toString()
                )
                productCount++
                Toast.makeText(this, "Datos capturados", Toast.LENGTH_SHORT).show()
                limpiar()
            } else {
                Toast.makeText(this, "Almacenamiento lleno", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Debe registrar datos", Toast.LENGTH_LONG).show()
        }
    }

    private fun buscarDatos() {
        val searchCode = codigo.text.toString()
        if (searchCode.isNotEmpty()) {
            var found = false
            for (i in 0 until productCount) {
                if (products[i]?.code == searchCode) {
                    descripcion.setText(products[i]?.description)
                    extra1.setText(products[i]?.info1)
                    extra2.setText(products[i]?.info2)
                    found = true
                    break
                }
            }
            if (!found) {
                Toast.makeText(this, "Código no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un código para buscar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiar() {
        codigo.text.clear()
        descripcion.text.clear()
        extra1.text.clear()
        extra2.text.clear()
        codigo.requestFocus()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an explicit contract API and eliminates most of the boilerplate involved in handling activity results.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Código leído", Toast.LENGTH_SHORT).show()
                codigo.setText(intentResult.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}