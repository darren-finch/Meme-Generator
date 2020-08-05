package com.darrenfinch.memegenerator.screens.selectmeme

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.memegenerator.usecases.GetRandomMemesUseCase
import com.darrenfinch.memegenerator.util.NavigationWrapper
import com.darrenfinch.memegenerator.util.ToastWrapper

class SelectMemeController(
    private val getRandomMemesUseCase: GetRandomMemesUseCase,
    private val toastWrapper: ToastWrapper,
    private val navigationWrapper: NavigationWrapper,
    private val viewLifecycleOwner: LifecycleOwner
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
        getRandomMemesUseCase.invoke().observe(viewLifecycleOwner, Observer { memePictures ->
            if (memePictures == null) {
                toastWrapper.showMessage("Couldn't get memes. Check your internet connection.")
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
        navigationWrapper.navigateToEditMemeScreen(memeId)
    }
}