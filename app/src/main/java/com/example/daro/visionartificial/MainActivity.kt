package com.example.daro.visionartificial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.key
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity() {

    lateinit var usuarios: ArrayList<Usuario>
    var estadoIngresoSistema = 0
    lateinit var valorIdUser:String
    lateinit var usuarioActual:String

    var usuario: Usuario? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener{
            v: View? ->  irActividadPrincipal()
        }

        btnActividadRegistro.setOnClickListener {
            v: View? ->  irActividadRegistroUsuario()
        }

    }


    fun irActividadRegistroUsuario(){
        val intent = Intent(this,RegistroUsuarioActivity::class.java)
        Toasty.info(this, "Completa el registro para usar la app", Toast.LENGTH_SHORT, true).show()
        startActivity(intent)
    }

    fun irActividadPrincipal(){

        val nombreUsuario = txtUsername.text.toString()
        val contrasenaUsuario = txtPassword.text.toString()

        //var usuario = Usuario(rolUsuario,nombreUsuario,contrasenaUsuario,0,0)
        //DatabaseUsuario.insertarUsuario(usuario)

        usuarios = DatabaseUsuario.getList()

        for (datos in usuarios){

            if ( datos.username.equals(nombreUsuario,true) && datos.password.equals(contrasenaUsuario,true)){
                estadoIngresoSistema = 1
                usuarioActual = datos.username
                valorIdUser = datos.idUser.toString()
            }

        }

        //Toast.makeText(this,"VALOR: $estadoIngresoSistema",Toast.LENGTH_SHORT).show()

        if (estadoIngresoSistema==1){
            Toasty.success(this, "Bienvenido al Sistema: $usuarioActual", Toast.LENGTH_LONG, true).show()
            val intent = Intent(this, NavigationActivity::class.java)


            /***ENVIO EL VALOR DEL ID DEL USUARIO AL FRAGMENTO PROFILE USER***/
            val prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("idUsuarioActual", valorIdUser)
            editor.commit()


            startActivity(intent)
        }else{

            Alerter.create(this)
                    .setTitle("Datos o Usuario Incorrectos")
                    .setText("Verifica tus datos e intenta nuevamente")
                    .setBackgroundColorRes(R.color.error_color_material)
                    .enableSwipeToDismiss()
                    .show()

            txtUsername.setText("")
            txtPassword.setText("")
        }

    }




}
