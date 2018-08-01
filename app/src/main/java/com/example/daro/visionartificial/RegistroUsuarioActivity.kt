package com.example.daro.visionartificial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registro_usuario.*

class RegistroUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        btnRegresarLogin.setOnClickListener {
            v: View? ->  irActividadLogin()
        }

        btnRegistrarUsuario.setOnClickListener{
            v: View? ->  guardarDatosUsuario()
        }
    }

    fun irActividadLogin(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }

    fun guardarDatosUsuario() {

        if (txtUsernameRegistro.text.toString().isEmpty() || txtPasswordRegistro.text.toString().isEmpty()){

            Toast.makeText(this,"CAMPOS VACIOS",Toast.LENGTH_SHORT).show()

        }else{

            var nombreUsuario = txtUsernameRegistro.text.toString()
            var contrasenaUsuario = txtPasswordRegistro.text.toString()

            var usuario = Usuario(0,nombreUsuario, contrasenaUsuario)
            DatabaseUsuario.insertarUsuario(usuario)

            Toast.makeText(this,"Datos Registrados",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)


        }



    }

}
