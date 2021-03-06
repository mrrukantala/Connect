package com.example.connect.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "saved_product_data")
data class SaveProductData(

    @PrimaryKey(autoGenerate = true)
    val idSaveProductData: Long = 0L,

    @ColumnInfo(name = "_id")
    var id: Int ,

    @ColumnInfo(name = "_name")
    var name: String ,

    @ColumnInfo(name = "_level")
    var level: String ,

    @ColumnInfo(name = "_photo")
    var photo: String,

    @ColumnInfo(name= "_gambar")
    var gambar: String,

    @ColumnInfo(name = "harga")
    var harga: Int ,

    @ColumnInfo(name = "_nama_produk")
    var nama_produk: String ,

    @ColumnInfo(name = "deskripsi")
    var deskripsi: String,

    @ColumnInfo(name = "wa")
    var wa: String?,

    @ColumnInfo(name = "id_user")
    var id_user: Int
)
