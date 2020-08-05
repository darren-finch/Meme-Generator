package com.darrenfinch.memegenerator.screens.editmeme

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.darrenfinch.memegenerator.usecases.GetMemeImageUseCase
import com.darrenfinch.memegenerator.util.FileUtils

//NOTE: This controller will not be unit testable until all dependencies on the fragment have been removed.
//TODO: Remove dependencies on fragment
class EditMemeController(private val viewModel: EditMemeViewModel,
                         private val getMemeImageUseCase: GetMemeImageUseCase,
                         private val memeId: Int,
                         private val fragment: EditMemeFragment) : EditMemeMvcView.EventListener {
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
        }
        catch (e: Exception) {
            Toast.makeText(fragment.requireContext(), "No meme with the id of $memeId could be found.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigateUp() {
        fragment.findNavController().navigateUp()
    }

    override fun onShareMeme(meme: Bitmap, memeName: String) {
        val uri = FileUtils.saveImage(fragment.requireContext(), meme, memeName)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        fragment.requireContext().startActivity(intent)
    }
}