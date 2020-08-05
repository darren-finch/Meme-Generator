package com.darrenfinch.memegenerator.screens.selectmeme

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.darrenfinch.memegenerator.usecases.GetRandomMemesUseCase
import com.darrenfinch.memegenerator.util.UserMessenger

//NOTE: This controller will not be unit testable until all dependencies on the fragment have been removed.
//TODO: Remove dependencies on fragment
class SelectMemeController(
    private val getRandomMemesUseCase: GetRandomMemesUseCase,
    private val fragment: SelectMemeFragment
) : SelectMemeMvcView.EventListener {
    private lateinit var mvcView: SelectMemeMvcView

    fun bindToView(selectMemeMvcView: SelectMemeMvcView) {
        mvcView = selectMemeMvcView
    }

    fun onStart() {
        mvcView.registerEventListener(this)
    }

    fun onStop() {
        mvcView.unregisterEventListener(this)
    }

    fun fetchMeme() {
        mvcView.showLoading()
        getRandomMemesUseCase.invoke().observe(fragment.viewLifecycleOwner, Observer { memePictures ->
            if (memePictures == null) {
                Toast.makeText(fragment.context, "Couldn't get memes. Check your internet connection.", Toast.LENGTH_SHORT).show()
            } else {
                mvcView.hideLoading()
                mvcView.bindMemes(memePictures)
            }
        })
    }

    override fun onRefreshClick() {
        fetchMeme()
    }

    override fun onMemeClick(memeId: Int) {
        fragment.findNavController().navigate(
            SelectMemeFragmentDirections.actionSelectMemeFragmentToEditMemeFragment(
                memeId
            )
        )
    }
}