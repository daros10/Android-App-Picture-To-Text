package com.example.daro.visionartificial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.key



class MainActivity : AppCompatActivity() {

    lateinit var usuarios: ArrayList<Usuario>
    var estadoIngresoSistema = 0
    lateinit var valorIdUser:String
    lateinit var usuarioActual:String


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
            Toast.makeText(this,"Bienvenido al Sistema: $usuarioActual $valorIdUser", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NavigationActivity::class.java)



            startActivity(intent)
        }else{
            Toast.makeText(this,"Datos o Usuario Incorrectos", Toast.LENGTH_SHORT).show()
        }

    }




}
