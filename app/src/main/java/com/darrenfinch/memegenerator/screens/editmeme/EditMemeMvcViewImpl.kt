package com.darrenfinch.memegenerator.screens.editmeme

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darrenfinch.memegenerator.R
import com.darrenfinch.memegenerator.databinding.FragmentEditMemeBinding
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.screens.common.BaseMvcView
import com.darrenfinch.memegenerator.screens.common.MvcView
import com.darrenfinch.memegenerator.util.FileUtils

class EditMemeMvcViewImpl(inflater: LayoutInflater, container: ViewGroup?, fragment: EditMemeFragment) : EditMemeMvcView,
    BaseMvcView<EditMemeMvcView.EventListener>() {
    private val binding = DataBindingUtil.inflate<FragmentEditMemeBinding>(inflater, R.layout.fragment_edit_meme, container, false).apply {
        toolbar.overflowIcon =
            fragment.resources.getDrawable(R.drawable.ic_more, fragment.context?.applicationContext?.theme)
        toolbar.inflateMenu(R.menu.edit_meme_menu)
        toolbar.setNavigationOnClickListener {
            for(listener in getListeners()) {
                listener.onNavigateUp()
            }
        }
        toolbar.setOnMenuItemClickListener { item ->
            onMenuItemClick(item)
        }
        increaseFontSizeButton.setOnClickListener {
            topText.setTextSize(TypedValue.COMPLEX_UNIT_PX, topText.textSize + 16.0f)
            bottomText.setTextSize(TypedValue.COMPLEX_UNIT_PX, bottomText.textSize + 16.0f)
        }
        decreaseFontSizeButton.setOnClickListener {
            topText.setTextSize(TypedValue.COMPLEX_UNIT_PX, topText.textSize - 16.0f)
            bottomText.setTextSize(TypedValue.COMPLEX_UNIT_PX, bottomText.textSize - 16.0f)
        }
        viewTopTextCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            topText.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        viewBottomTextCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            bottomText.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.share -> {
                for(listener in getListeners()) {
                    listener.onShareMeme(screenshot(binding.memeImageLayout), binding.topText.text.toString())
                }
            }
        }
        return true
    }

    private fun screenshot(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun bindMeme(meme: ObservableMeme) {
        binding.observableMeme = meme
    }

    override fun getAndroidView(): View {
        return binding.root
    }
}