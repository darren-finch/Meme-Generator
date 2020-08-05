package com.darrenfinch.memegenerator.usecases

import androidx.lifecycle.LiveData
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.models.MemePicture

class GetRandomMemesUseCase(private val repository: MemeRepository) {
    fun invoke() : LiveData<List<MemePicture>> {
        return repository.getRandomMemePictures()
    }
}
