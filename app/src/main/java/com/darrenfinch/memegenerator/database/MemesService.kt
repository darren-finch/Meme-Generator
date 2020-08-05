package com.darrenfinch.memegenerator.database

import retrofit2.Call
import retrofit2.http.GET

interface MemesService {
    @GET("/get_memes")
    fun getAllMemes() : Call<MemeListResponse>
}