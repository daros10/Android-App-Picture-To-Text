package com.example.daro.visionartificial

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class FotografiaGuardadaAdapter(private val fotografiaAnalisisList: List<FotografiaTexto>) :  RecyclerView.Adapter<FotografiaGuardadaAdapter.MyViewHolder>(){

    lateinit var myBitmapAgain: Bitmap
    var picAnalizada: FotografiaTexto? = null

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var valorImagenAnalisada: TextView


        var fotografiaProcesada : ImageView


        //var detalles: Button

        lateinit var fotograffiaAnalisis: FotografiaTexto

        init {
            valorImagenAnalisada = view.findViewById(R.id.txtDato1) as TextView

            fotografiaProcesada = view.findViewById(R.id.imageViewDato)

            //detalles = view.findViewById(R.id.btnDetalles) as Button

            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            /*menu?.add(Menu.NONE, R.id.item_menu_compartir, Menu.NONE, R.string.menu_share)*/
            //menu?.add(Menu.NONE, R.id.item_menu_editar, Menu.NONE, "Editar")
             menu?.add(Menu.NONE, R.id.item_menu_eliminar, Menu.NONE, "Eliminar")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.reyclerview_busquedas_layout, parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val imagenProcesada = fotografiaAnalisisList[position]

        holder.valorImagenAnalisada.text = imagenProcesada.valorTextoProcesado

        //debo transformar a bitmap la imagen
        myBitmapAgain = decodeBase64(imagenProcesada.imagenAProcesar)

        holder.fotografiaProcesada.setImageBitmap(myBitmapAgain)


       /* holder.detalles.setOnClickListener{
            /*v: View ->
            val intent = Intent(v.context, ProfileUserFragment::class.java)
            //intent.putExtra("detallesPokemon", pokemonN)
            v.context.startActivity(intent)*/
        }*/
        holder.itemView.setOnLongClickListener {
            setPosition(holder.adapterPosition)
            false
        }
    }

    override fun getItemCount(): Int {
        return fotografiaAnalisisList.size
    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes =  Base64.decode(input,0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


}