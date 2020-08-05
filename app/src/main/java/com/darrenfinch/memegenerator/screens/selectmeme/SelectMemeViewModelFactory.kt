package com.darrenfinch.memegenerator.screens.selectmeme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.memegenerator.database.MemeRepository

@Suppress("UNCHECKED_CAST")
class SelectMemeViewModelFactory(private val repo: MemeRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SelectMemeViewModel(repo) as T
    }
}