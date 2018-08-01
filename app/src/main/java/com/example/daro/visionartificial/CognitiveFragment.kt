package com.example.daro.visionartificial

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_principal_navegacion.*
import kotlinx.android.synthetic.main.fragment_cognitive.*
import java.io.ByteArrayOutputStream
import android.text.SpannableStringBuilder
import android.text.Editable
import android.content.Intent.getIntent
import android.content.Intent.getIntent
import android.content.Intent.getIntent

class CognitiveFragment : Fragment(), View.OnClickListener  {

    lateinit var buttonTakePicture: Button
    lateinit var buttonAnalizar: Button
    lateinit var buttonGuardaDatos : Button
    lateinit var rootView: View
    lateinit var myBase64Image:String
    lateinit var myBitmapAgain:Bitmap
    private lateinit var imageBitmap: Bitmap

    lateinit var resultadoFinalTexto :String







    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cognitive, container, false)

        buttonTakePicture = rootView.findViewById(R.id.btnTomarFotografia) as Button
        buttonAnalizar = rootView.findViewById(R.id.btnAnalizar) as Button
        buttonGuardaDatos = rootView.findViewById(R.id.btnGuardarAnalisis) as Button

        buttonTakePicture.setOnClickListener(this)
        buttonAnalizar.setOnClickListener(this)
        buttonGuardaDatos.setOnClickListener(this)




        return rootView
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTomarFotografia -> {
                tomarFoto()
            }
            R.id.btnAnalizar -> {
                analizarFoto()
            }
            R.id.btnGuardarAnalisis ->{
                guardarDatosAnalisis()
            }

        }
    }


    val REQUEST_IMAGE_CAPTURE = 1

    private fun tomarFoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val extras = data.extras
            imageBitmap = extras!!.get("data") as Bitmap

            myBase64Image = encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG, 100)
            myBitmapAgain = decodeBase64(myBase64Image)

            imageViewFotografia.setImageBitmap(myBitmapAgain)

            Toast.makeText(activity,"Dentro de camara: $myBase64Image", Toast.LENGTH_SHORT).show()

        }

    }

    fun encodeToBase64(image: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String {
        val byteArrayOS = ByteArrayOutputStream()
        image.compress(compressFormat, quality, byteArrayOS)
        return android.util.Base64.encodeToString(byteArrayOS.toByteArray(), android.util.Base64.DEFAULT)

    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes =  Base64.decode(input,0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun analizarFoto(){
        Toast.makeText(activity, "Procesando...", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(activity, "No Text", Toast.LENGTH_LONG).show()
            return
        }
        for (block in text.blocks) {
            val txt = block.text

            resultadoFinalTexto = txt.replace("\n"," ")

        }

        val editable = SpannableStringBuilder(resultadoFinalTexto)

        txtResultadoProcesamiento.text = editable

        Toast.makeText(activity, resultadoFinalTexto, Toast.LENGTH_LONG).show()




    }

    fun guardarDatosAnalisis(){
        var valorTextoProcesado = txtResultadoProcesamiento.text.toString().replace("\n","")
        var imagenAProcesar = myBase64Image

        //Toast.makeText(activity,"texto: $valorTextoProcesado", Toast.LENGTH_SHORT).show()
        //Toast.makeText(activity,"image: $imagenAProcesar", Toast.LENGTH_SHORT).show()

        var fotografiaTexto = FotografiaTexto(valorTextoProcesado,imagenAProcesar,1)
        DatabaseFotografiaTexto.insertarAnalisis(fotografiaTexto)



        Toast.makeText(activity,"SAVEDDD", Toast.LENGTH_SHORT).show()


    }








}

