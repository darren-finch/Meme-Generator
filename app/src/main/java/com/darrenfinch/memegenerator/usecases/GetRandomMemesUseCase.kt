package com.darrenfinch.memegenerator.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.darrenfinch.memegenerator.database.MemeListResponse
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.util.NUM_OF_RANDOM_MEMES

class GetRandomMemesUseCase(private val repository: MemeRepository) {
    //This is doable because I want to re-fetch on a configuration change.
    //But you would want to store this live data in your view model otherwise
    private val randomMemeLiveData: LiveData<List<MemePicture>>
    get() {
        return Transformations.map(repository.getAllMemePictures()) { allMemesList ->
            generateRandomMemePictures(allMemesList)
        }
    }
    fun invoke() : LiveData<List<MemePicture>> {
        return randomMemeLiveData
    }
    private fun generateRandomMemePictures(memePictures: List<MemePicture>) : List<MemePicture> {
        val randomMemesList = mutableListOf<MemePicture>()
        for(i in 0..NUM_OF_RANDOM_MEMES) {
            randomMemesList.add(memePictures[((Math.random() * memePictures.size).toInt())])
        }
        return randomMemesList
    }
}
