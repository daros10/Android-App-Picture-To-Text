package com.example.daro.visionartificial

import android.os.Parcel
import android.os.Parcelable

class FotografiaTexto( val valorTextoProcesado:String, val imagenAProcesar:String, val idUsuarioUpload:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(valorTextoProcesado)
        parcel.writeString(imagenAProcesar)
        parcel.writeInt(idUsuarioUpload)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FotografiaTexto> {
        override fun createFromParcel(parcel: Parcel): FotografiaTexto {
            return FotografiaTexto(parcel)
        }

        override fun newArray(size: Int): Array<FotografiaTexto?> {
            return arrayOfNulls(size)
        }
    }
}
