package com.example.daro.visionartificial

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_principal_navegacion.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import android.R.attr.bitmap
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import android.widget.Toast

class PrincipalNavegacion : AppCompatActivity() {


    private lateinit var imageBitmap: Bitmap


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_navegacion)


        btnTomarFoto.setOnClickListener{
            v: View? ->  tomarFoto()
        }

        btnAnalizarFoto.setOnClickListener {
            v: View? ->  analizarFoto()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


    fun analizarFoto(){
        Toast.makeText(this, "Procesando...", Toast.LENGTH_SHORT).show()
        val image = FirebaseVisionImage.fromBitmap(imageBitmap)
        val detector = FirebaseVision.getInstance().visionTextDetector
        //detector.detectInImage(image).addOnSuccessListener()

        detector.detectInImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    // Task completed successfully
                    // ...
                    procesTxt(firebaseVisionText)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }

    }

    fun procesTxt(text: FirebaseVisionText){

        val blocks = text.blocks
        if (blocks.size == 0) {
            Toast.makeText(this, "No Text", Toast.LENGTH_LONG).show()
            return
        }
        for (block in text.blocks) {
            val txt = block.text
            txtResultadoFoto.text = txt
            Toast.makeText(this, txt, Toast.LENGTH_LONG).show()


        }


    }


    val REQUEST_IMAGE_CAPTURE = 1


    private fun tomarFoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data.extras
            imageBitmap = extras!!.get("data") as Bitmap
            imageViewFoto.setImageBitmap(imageBitmap)
        }
    }
}
