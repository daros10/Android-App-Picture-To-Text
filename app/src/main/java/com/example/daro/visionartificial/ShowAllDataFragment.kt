package com.example.daro.visionartificial

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView




class ShowAllDataFragment : Fragment(){

    lateinit var fotografiaAnalisis: ArrayList<FotografiaTexto>
    lateinit var adaptador: FotografiaAnalisisAdapter
    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_recyclerview_all_data,container,false)


        fotografiaAnalisis = DatabaseFotografiaTexto.getFotografiasList()
        //Log.d("resultado",pokemon.toString())

       val showDataPicture_recyclerview = rootView.findViewById(R.id.recyclerViewShowAllData) as RecyclerView


        val layoutManager = LinearLayoutManager(context)
        adaptador = FotografiaAnalisisAdapter(fotografiaAnalisis)
        showDataPicture_recyclerview.layoutManager = layoutManager
        showDataPicture_recyclerview.itemAnimator = DefaultItemAnimator()
        showDataPicture_recyclerview.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(showDataPicture_recyclerview)


        return rootView
    }
}