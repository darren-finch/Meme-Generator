package com.darrenfinch.memegenerator.screens.selectmeme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.darrenfinch.memegenerator.database.MemeListResponse
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.util.NUM_OF_RANDOM_MEMES
import retrofit2.Response

class SelectMemeViewModel(private val repo: MemeRepository) : ViewModel() {
    private val randomMemePicturesLiveData: LiveData<List<MemePicture>>
    get() {
        return Transformations.map(repo.getMemeList()) { memeListResponse ->
            generateRandomMemePictures(memeListResponse)
        }
    }

    private fun generateRandomMemePictures(response: MemeListResponse) : List<MemePicture> {
        val allMemesList = response.data.memePictures
        val randomMemesList = mutableListOf<MemePicture>()
        for(i in 0..NUM_OF_RANDOM_MEMES) {
            randomMemesList.add(allMemesList[((Math.random() * allMemesList.size).toInt())])
        }
        return randomMemesList
    }

    fun getRandomMemePictures() : LiveData<List<MemePicture>> {
        return randomMemePicturesLiveData
    }
}