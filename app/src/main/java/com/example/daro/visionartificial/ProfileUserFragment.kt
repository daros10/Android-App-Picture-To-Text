package com.example.daro.visionartificial

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


class ProfileUserFragment : Fragment(){

    lateinit var fotografiaAnalisis: ArrayList<FotografiaTexto>
    lateinit var adaptador: FotografiaGuardadaAdapter
    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_profile_user,container,false)



        /**RECUPERO EL DATO ENVIADO DESDE ACTIVITY **/
        val prefs = activity!!.getSharedPreferences("Preferences", 0)
        val idUsuarioActual = prefs.getString("idUsuarioActual", "")



        fotografiaAnalisis = DatabaseFotografiaTexto.getUserFotografiasList(idUsuarioActual.toInt())


        val showDataPicture_recyclerview = rootView.findViewById(R.id.recyclerView_bussquedas_usuario) as RecyclerView


        val layoutManager = LinearLayoutManager(context)
        adaptador = FotografiaGuardadaAdapter(fotografiaAnalisis)
        showDataPicture_recyclerview.layoutManager = layoutManager
        showDataPicture_recyclerview.itemAnimator = DefaultItemAnimator()
        showDataPicture_recyclerview.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(showDataPicture_recyclerview)



        return rootView
    }


}