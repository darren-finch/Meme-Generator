package com.darrenfinch.memegenerator.util

import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.database.MemesService
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeViewModelFactory
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InjectionUtils {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgflip.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    private val api = retrofit.create(MemesService::class.java)
    private val memeRepository = MemeRepository(api)
    private fun provideMemeRepository() = memeRepository
    fun provideSelectMemeViewModelFactory() = SelectMemeViewModelFactory(provideMemeRepository())
    fun provideEditMemeViewModelFactory() = EditMemeViewModelFactory(provideMemeRepository())
}