package com.example.practica2

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FotoActivity : AppCompatActivity() {
    private lateinit var foto: ImageView
    private var imgBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        foto = findViewById(R.id.imgFoto)
        val btnTomar: Button = findViewById(R.id.btnTomar)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)

        btnTomar.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            responseLauncher.launch(intent)
        }
        btnGuardar.setOnClickListener {
            guardarFoto()
        }
    }

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                Toast.makeText(this, "Fotografía tomada!!", Toast.LENGTH_SHORT).show()
                val extras = activityResult.data!!.extras
                imgBitmap = extras!!["data"] as Bitmap?
                foto.setImageBitmap(imgBitmap)
            } else {
                Toast.makeText(this, "Proceso cancelado", Toast.LENGTH_SHORT).show()
            }
        }

    private fun guardarFoto() {
        if (imgBitmap != null) {
            val fos: OutputStream
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = contentResolver
                    val contentValues = ContentValues()
                    contentValues.put(
                        MediaStore.MediaColumns.DISPLAY_NAME,
                        "image_" + System.currentTimeMillis() + ".jpg"
                    )
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    contentValues.put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + "Practica2"
                    )
                    val imageUri =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    fos = resolver.openOutputStream(imageUri!!)!!
                } else {
                    val imagesDir =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator + "Practica2")
                    if (!imagesDir.exists()) {
                        imagesDir.mkdir()
                    }
                    val image = File(imagesDir, "image_" + System.currentTimeMillis() + ".jpg")
                    fos = FileOutputStream(image)
                }
                imgBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                Toast.makeText(this, "Fotografía guardada", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Primero debe tomar una fotografía", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}