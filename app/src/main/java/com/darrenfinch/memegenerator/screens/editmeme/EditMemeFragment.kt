package com.darrenfinch.memegenerator.screens.editmeme

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darrenfinch.memegenerator.R
import com.darrenfinch.memegenerator.databinding.FragmentEditMemeBinding
import com.darrenfinch.memegenerator.util.FileUtils
import com.darrenfinch.memegenerator.util.InjectionUtils
import java.lang.IllegalArgumentException


class EditMemeFragment : Fragment() {
    private val viewModel: EditMemeViewModel by viewModels {
        InjectionUtils.provideEditMemeViewModelFactory()
    }

    private lateinit var binding: FragmentEditMemeBinding

    private val args: EditMemeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEditMemeBinding>(inflater, R.layout.fragment_edit_meme, container, false).apply {
            toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_more, context?.applicationContext?.theme)
            toolbar.inflateMenu(R.menu.edit_meme_menu)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            toolbar.setOnMenuItemClickListener { item ->
                handleMenuItemClick(item)
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
                topText.visibility = if(isChecked) View.VISIBLE else View.GONE
            }
            viewBottomTextCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
                bottomText.visibility = if(isChecked) View.VISIBLE else View.GONE
            }

            observableMeme = viewModel.observableMeme
            try {
                viewModel.fetchMeme(args.memeId)
            }
            catch (e: Exception) {
                Toast.makeText(context, "No meme with the id of ${args.memeId} could be found.", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    private fun handleMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareMeme()
        }
        return true
    }

    private fun shareMeme() {
        val bitmap = screenshot(binding.memeImageLayout)
        val uri = FileUtils.saveImage(requireContext(), bitmap, binding.topText.text.toString())
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
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
}