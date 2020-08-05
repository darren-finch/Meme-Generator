package com.darrenfinch.memegenerator.usecases

import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.models.MemePicture

class GetMemeImageUseCase(private val repository: MemeRepository) {
    fun invoke(memeId: Int) : MemePicture {
        return repository.getMemeImage(memeId)
    }
}