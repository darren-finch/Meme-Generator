package com.darrenfinch.memegenerator.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.darrenfinch.memegenerator.database.MemeRepository
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeController
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeFragment
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeMvcViewImpl
import com.darrenfinch.memegenerator.screens.editmeme.EditMemeViewModel
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeController
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeFragment
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeMvcViewImpl
import com.darrenfinch.memegenerator.usecases.GetMemeImageUseCase
import com.darrenfinch.memegenerator.usecases.GetRandomMemesUseCase

object InjectionUtils {
    private val memeRepository = MemeRepository()

    //Repository
    private fun provideMemeRepository() = memeRepository

    //Views
    fun provideSelectMemeMvcView(
        context: Context,
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =
        SelectMemeMvcViewImpl(context, inflater, container)

    fun provideEditMemeMvcView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        fragment: EditMemeFragment
    ) = EditMemeMvcViewImpl(inflater, container, fragment)

    //Controllers
    fun provideSelectMemeController(fragment: SelectMemeFragment) = SelectMemeController(
        provideGetRandomMemesUseCase(),
        provideToastWrapper(fragment.requireContext()),
        provideNavigationWrapper(fragment.findNavController()),
        fragment.viewLifecycleOwner
    )

    fun provideEditMemeController(
        viewModel: EditMemeViewModel,
        memeId: Int,
        fragment: EditMemeFragment
    ) = EditMemeController(
        viewModel,
        provideGetMemeImageUseCase(),
        memeId,
        provideToastWrapper(fragment.requireContext()),
        provideNavigationWrapper(fragment.findNavController()),
        fragment.requireContext()
    )

    //Use cases
    private fun provideGetRandomMemesUseCase() = GetRandomMemesUseCase(provideMemeRepository())
    private fun provideGetMemeImageUseCase() = GetMemeImageUseCase(provideMemeRepository())

    //Utils
    private fun provideToastWrapper(context: Context) = ToastWrapper(context)
    private fun provideNavigationWrapper(navController: NavController) =
        NavigationWrapper(navController)
}