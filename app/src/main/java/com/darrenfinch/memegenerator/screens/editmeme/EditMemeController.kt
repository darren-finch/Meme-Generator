package com.darrenfinch.memegenerator.screens.editmeme

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.darrenfinch.memegenerator.usecases.GetMemeImageUseCase
import com.darrenfinch.memegenerator.util.FileUtils
import com.darrenfinch.memegenerator.util.NavigationWrapper
import com.darrenfinch.memegenerator.util.ToastWrapper

class EditMemeController(
    private val viewModel: EditMemeViewModel,
    private val getMemeImageUseCase: GetMemeImageUseCase,
    private val memeId: Int,
    private val toastWrapper: ToastWrapper,
    private val navigationWrapper: NavigationWrapper,
    private val context: Context
) : EditMemeMvcView.EventListener {
    private lateinit var mvcView: EditMemeMvcView

    fun bindView(editMemeMvcView: EditMemeMvcView) {
        mvcView = editMemeMvcView
        mvcView.bindMeme(viewModel.observableMeme)
    }

    fun onStart() {
        mvcView.registerEventListener(this)
    }

    fun onStop() {
        mvcView.unregisterEventListener(this)
    }

    fun fetchMeme() {
        try {
            viewModel.observableMeme.set(getMemeImageUseCase.invoke(memeId))
        } catch (e: Exception) {
            toastWrapper.showMessage("No meme with the id of $memeId could be found.")
        }
    }

    override fun onNavigateUp() {
        navigationWrapper.navigateUp()
    }

    override fun onShareMeme(meme: Bitmap, memeName: String) {
        val uri = FileUtils.saveImage(context, meme, memeName)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        context.startActivity(intent)
    }
}