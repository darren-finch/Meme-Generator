package com.darrenfinch.memegenerator.database

import com.darrenfinch.memegenerator.models.MemePicture
import com.google.gson.annotations.SerializedName

data class MemeListResponse (val success: Boolean, val data: DataResponse)

data class DataResponse(@SerializedName("memes") val memePictures: List<MemePicture>)
