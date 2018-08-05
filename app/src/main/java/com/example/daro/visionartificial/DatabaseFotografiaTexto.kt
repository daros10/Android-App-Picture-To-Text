package com.example.daro.visionartificial

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

class DatabaseFotografiaTexto{
    companion object {


        fun insertarAnalisis(fotografiaTexto : FotografiaTexto){
            "http://192.168.0.3:1337/FotografiaTexto".httpPost(listOf("valorTextoProcesado" to fotografiaTexto.valorTextoProcesado, "imagenFotografiaProcesar" to fotografiaTexto.imagenAProcesar, "usuarioId" to fotografiaTexto.idUsuarioUpload ))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun getFotografiasList(): ArrayList<FotografiaTexto> {
            val fotografiaAnalisis: ArrayList<FotografiaTexto> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.0.3:1337/FotografiaTexto".httpGet().responseString()
            val jsonStringPokemon = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringPokemon)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val valorTextoImage = it["valorTextoProcesado"] as String
                val imagenProcesada= it["imagenFotografiaProcesar"] as String
                //val latitud = it["latitud"] as Double
                // val longitud = it["longitud"] as Double
                val fotoProcesada = FotografiaTexto(valorTextoImage,imagenProcesada,1)
                fotografiaAnalisis.add(fotoProcesada)
            }
            return fotografiaAnalisis
        }


        fun getUserFotografiasList(usuarioId:Int): ArrayList<FotografiaTexto> {
            val fotografiaAnalisis: ArrayList<FotografiaTexto> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.0.3:1337/FotografiaTexto?usuarioId=$usuarioId".httpGet().responseString()
            val jsonStringPokemon = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringPokemon)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val valorTextoImage = it["valorTextoProcesado"] as String
                val imagenProcesada= it["imagenFotografiaProcesar"] as String
                //val latitud = it["latitud"] as Double
                // val longitud = it["longitud"] as Double
                val fotoProcesada = FotografiaTexto(valorTextoImage,imagenProcesada,1)
                fotografiaAnalisis.add(fotoProcesada)
            }
            return fotografiaAnalisis
        }
    }
}