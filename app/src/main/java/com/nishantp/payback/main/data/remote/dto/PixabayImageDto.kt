package com.nishantp.payback.main.data.remote.dto


import com.google.gson.annotations.SerializedName

data class PixabayImageDto(
    @SerializedName("hits") val hitDtos: List<HitDto>,
    @SerializedName("total") val total: Int,
    @SerializedName("totalHits") val totalHits: Int
)