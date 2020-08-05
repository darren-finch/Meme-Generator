package com.darrenfinch.memegenerator.screens.selectmeme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darrenfinch.memegenerator.database.MemeListResponse
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.models.MemePicture

class SelectMemeViewModel(private val repo: MemeRepository) : ViewModel() {
    fun getRandomMemePictures() : LiveData<List<MemePicture>> {
        return repo.getRandomMemePictures()
    }
}