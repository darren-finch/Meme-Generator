package com.darrenfinch.memegenerator.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.util.NUM_OF_RANDOM_MEMES
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class MemeRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgflip.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val client = retrofit.create(MemesService::class.java)
    private val memeListLiveData = MutableLiveData<List<MemePicture>>()

    fun getRandomMemePictures() : LiveData<List<MemePicture>> {
        client.getAllMemes().enqueue(object : Callback<MemeListResponse> {
            override fun onFailure(call: Call<MemeListResponse>, t: Throwable) {
                memeListLiveData.postValue(null)
            }
            override fun onResponse(call: Call<MemeListResponse>, response: Response<MemeListResponse>) {
                if(response.body() != null) {
                    val randomMemePictures = generateRandomMemePictures(response.body()!!)
                    memeListLiveData.postValue(randomMemePictures)
                }
            }
        })
        return memeListLiveData
    }

    private fun generateRandomMemePictures(response: MemeListResponse) : List<MemePicture> {
        val allMemesList = response.data.memePictures
        val randomMemesList = mutableListOf<MemePicture>()
        for(i in 0..NUM_OF_RANDOM_MEMES) {
            randomMemesList.add(allMemesList[((Math.random() * allMemesList.size).toInt())])
        }
        return randomMemesList
    }

    //If there is a way to query this meme generator API, I haven't found it or I'm too dumb
    //So I'm doing a simple linear search here
    fun getMemeImage(memeId: Int) : MemePicture {
        if(memeListLiveData.value == null) {
            throw IllegalStateException("Meme List Live Data was null. Not good.")
        }

        for(memePicture in memeListLiveData.value!!) {
            if(memePicture.id == memeId)
                return memePicture
        }

        throw IllegalArgumentException("No meme with the id of $memeId exists.")
    }
}