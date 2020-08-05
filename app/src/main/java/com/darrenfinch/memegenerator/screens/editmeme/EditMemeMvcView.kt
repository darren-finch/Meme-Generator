package com.darrenfinch.memegenerator.screens.editmeme

import android.graphics.Bitmap
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.screens.common.MvcView

interface EditMemeMvcView : MvcView<EditMemeMvcView.EventListener> {
    interface EventListener {
        fun onNavigateUp()
        fun onShareMeme(meme: Bitmap, memeName: String)
    }

    fun bindMeme(meme: ObservableMeme)
}