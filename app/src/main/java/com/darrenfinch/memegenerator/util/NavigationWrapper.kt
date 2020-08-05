package com.darrenfinch.memegenerator.util

import androidx.navigation.NavController
import com.darrenfinch.memegenerator.screens.selectmeme.SelectMemeFragmentDirections

class NavigationWrapper(private val navController: NavController) {
    fun navigateToEditMemeScreen(memeId: Int) {
        navController.navigate(
            SelectMemeFragmentDirections.actionSelectMemeFragmentToEditMemeFragment(
                memeId
            )
        )
    }
    fun navigateUp() {
        navController.navigateUp()
    }
}