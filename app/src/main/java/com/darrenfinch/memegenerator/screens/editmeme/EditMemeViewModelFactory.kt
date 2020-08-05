package com.darrenfinch.memegenerator.screens.editmeme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.memegenerator.database.MemeRepository

@Suppress("UNCHECKED_CAST")
class EditMemeViewModelFactory(private val repo: MemeRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditMemeViewModel(repo) as T
    }
}