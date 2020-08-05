package com.darrenfinch.memegenerator.util

import androidx.lifecycle.ViewModelProvider
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeViewModelFactory
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeViewModel
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeViewModelFactory

object InjectionUtils {
    private val memeRepository = MemeRepository()
    private fun provideMemeRepository() = memeRepository
    fun provideSelectMemeViewModelFactory() = SelectMemeViewModelFactory(provideMemeRepository())
    fun provideEditMemeViewModelFactory() = EditMemeViewModelFactory(provideMemeRepository())
}