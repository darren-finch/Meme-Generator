package com.darrenfinch.memegenerator.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.util.NUM_OF_RANDOM_MEMES
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class MemeRepository(private val api: MemesService) {
    private val TAG = "MemeRepository"

    private val memeListResponseLiveData = MutableLiveData<MemeListResponse>()

    fun getMemeList() : LiveData<MemeListResponse> {
        api.getAllMemes().enqueue(object : Callback<MemeListResponse> {
            override fun onFailure(call: Call<MemeListResponse>, t: Throwable) {
                memeListResponseLiveData.postValue(null)
                Log.e(TAG, "Failed to get meme list. Cause: ${t.message}")
            }
            override fun onResponse(call: Call<MemeListResponse>, response: Response<MemeListResponse>) {
                memeListResponseLiveData.postValue(response.body())
            }
        })
        return memeListResponseLiveData
    }

    //If there is a way to query this meme generator API, I haven't found it or I'm too dumb
    //So I'm doing a simple linear search here
    fun getMemeImage(memeId: Int) : MemePicture {
        if(memeListResponseLiveData.value == null) {
            throw IllegalStateException("Meme List Live Data was null. Not good.")
        }

        for(memePicture in memeListResponseLiveData.value!!.data.memePictures) {
            if(memePicture.id == memeId)
                return memePicture
        }

        throw IllegalArgumentException("No meme with the id of $memeId exists.")
    }
}