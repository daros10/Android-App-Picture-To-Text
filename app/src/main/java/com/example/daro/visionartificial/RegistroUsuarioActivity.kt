package com.example.daro.visionartificial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty
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

            Alerter.create(this)
                    .setTitle("Campos Vacios")
                    .setText("Completa la informacion de todos los campos")
                    .setBackgroundColorRes(R.color.error_color_material)
                    .enableSwipeToDismiss()
                    .show()

        }else{

            var nombreUsuario = txtUsernameRegistro.text.toString()
            var contrasenaUsuario = txtPasswordRegistro.text.toString()

            var usuario = Usuario(0,nombreUsuario, contrasenaUsuario)
            DatabaseUsuario.insertarUsuario(usuario)

            Toasty.success(this, "Datos registrados", Toast.LENGTH_LONG, true).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }



    }

}
