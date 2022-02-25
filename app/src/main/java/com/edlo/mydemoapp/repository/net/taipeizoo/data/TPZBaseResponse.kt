package com.edlo.mydemoapp.repository.net.taipeizoo

import com.google.gson.annotations.SerializedName

data class TPZBaseResponse<T> (
    @SerializedName("result") val result: TPZBaseResponseResult<T>,
)

data class TPZBaseResponseResult<T> (
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("sort") val sort: String,
    @SerializedName("results") val results: T,
)
