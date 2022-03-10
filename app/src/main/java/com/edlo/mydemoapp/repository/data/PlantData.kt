package com.edlo.mydemoapp.repository.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Fts4 //support full-text search(FTS)
@Entity(tableName = "table_plant")
data class PlantData(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    @SerializedName("_id") val id: Int,

    @ColumnInfo(name = "name_en")
    @SerializedName("F_Name_En") val nameEn: String,

    @ColumnInfo(name = "name_latin")
    @SerializedName("F_Name_Latin") val nameLatin: String,

    @ColumnInfo(name = "name_ch")
    @SerializedName("\uFEFFF_Name_Ch") val nameCh: String,

    @ColumnInfo
    @SerializedName("F_Location") val location: String,

    @ColumnInfo
    @SerializedName("F_Summary") val summary: String,

    @ColumnInfo
    @SerializedName("F_Keywords") val keywords: String,

    @ColumnInfo
    @SerializedName("F_Code") val code: String,

    @ColumnInfo
    @SerializedName("F_Geo") val geo: String,

    @ColumnInfo(name = "also_known")
    @SerializedName("F_AlsoKnown") val alsoKnown: String,

    @ColumnInfo
    @SerializedName("F_Brief") val brief: String,

    @ColumnInfo
    @SerializedName("F_Feature") val feature: String,

    @ColumnInfo
    @SerializedName("F_Family") val family: String,

    @ColumnInfo
    @SerializedName("F_CID") val cid: String,

    @ColumnInfo
    @SerializedName("F_Genus") val genus: String,

    @ColumnInfo(name = "function_and_application")
    @SerializedName("F_Function＆Application") val functionAndApplication: String,

    @ColumnInfo
    @SerializedName("F_Update") val update: String,

    @ColumnInfo(name = "pic01url")
    @SerializedName("F_Pic01_URL") val pic01URL: String,

    @ColumnInfo(name = "pic01alt")
    @SerializedName("F_Pic01_ALT") val pic01ALT: String,

    @ColumnInfo(name = "pic02url")
    @SerializedName("F_Pic02_URL") val pic02URL: String,

    @ColumnInfo(name = "pic02alt")
    @SerializedName("F_Pic02_ALT") val pic02ALT: String,

    @ColumnInfo(name = "pic03url")
    @SerializedName("F_Pic03_URL") val pic03URL: String,

    @ColumnInfo(name = "pic03alt")
    @SerializedName("F_Pic03_ALT") val pic03ALT: String,

    @ColumnInfo(name = "pic04url")
    @SerializedName("F_Pic04_URL") val pic04URL: String,

    @ColumnInfo(name = "pic04alt")
    @SerializedName("F_Pic04_ALT") val pic04ALT: String,

    @ColumnInfo(name = "pdf01url")
    @SerializedName("F_pdf01_URL") val pdf01URL: String,

    @ColumnInfo(name = "pdf01alt")
    @SerializedName("F_pdf01_ALT") val pdf01ALT: String,

    @ColumnInfo(name = "pdf02url")
    @SerializedName("F_pdf02_URL") val pdf02URL: String,

    @ColumnInfo(name = "pdf02alt")
    @SerializedName("F_pdf02_ALT") val pdf02ALT: String,

    @ColumnInfo(name = "vediourl")
    @SerializedName("F_Vedio_URL") val vedioURL: String,

    @ColumnInfo(name = "voice01url")
    @SerializedName("F_Voice01_URL") val voice01URL: String,

    @ColumnInfo(name = "voice01alt")
    @SerializedName("F_Voice01_ALT") val voice01ALT: String,

    @ColumnInfo(name = "voice02url")
    @SerializedName("F_Voice02_URL") val voice02URL: String,

    @ColumnInfo(name = "voice02alt")
    @SerializedName("F_Voice02_ALT") val voice02ALT: String,

    @ColumnInfo(name = "voice03url")
    @SerializedName("F_Voice03_URL") val voice03URL: String,

    @ColumnInfo(name = "voice03alt")
    @SerializedName("F_Voice03_ALT") val voice03ALT: String,
)