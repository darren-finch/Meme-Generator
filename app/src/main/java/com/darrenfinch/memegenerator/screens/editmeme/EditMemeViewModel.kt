package com.darrenfinch.memegenerator.screens.editmeme

import androidx.lifecycle.ViewModel
import com.darrenfinch.memegenerator.database.MemeRepository

class EditMemeViewModel(private val repo: MemeRepository) : ViewModel() {
    val observableMeme = ObservableMeme()
    fun fetchMeme(memeId: Int) {
        observableMeme.set(repo.getMemeImage(memeId))
    }
}