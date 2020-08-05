package com.darrenfinch.memegenerator.screens.selectmeme

import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.screens.common.MvcView

interface SelectMemeMvcView : MvcView<SelectMemeMvcView.EventListener> {
    interface EventListener {
        fun onRefreshClick()
        fun onMemeClick(memeId: Int)
    }

    fun bindMemes(memes: List<MemePicture>)
    fun showLoading()
    fun hideLoading()
}