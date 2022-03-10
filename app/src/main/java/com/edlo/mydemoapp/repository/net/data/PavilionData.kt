package com.edlo.mydemoapp.repository.net.taipeizoo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Fts4 //support full-text search(FTS)
@Entity(tableName = "table_pavilion")
class PavilionData(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    @SerializedName("_id") val id: Int,

    @ColumnInfo(name = "picurl")
    @SerializedName("E_Pic_URL") val picURL: String,

    @ColumnInfo
    @SerializedName("E_Geo") val geo: String,

    @ColumnInfo
    @SerializedName("E_Info") val info: String,

    @ColumnInfo
    @SerializedName("E_no") val no: String,

    @ColumnInfo
    @SerializedName("E_Category") val category: String,

    @ColumnInfo
    @SerializedName("E_Name") val name: String,

    @ColumnInfo
    @SerializedName("E_Memo") val memo: String,

    @ColumnInfo
    @SerializedName("E_URL") val url: String,
)