package com.darrenfinch.memegenerator.screens.editmeme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.darrenfinch.memegenerator.util.InjectionUtils


class EditMemeFragment : Fragment() {
    private lateinit var controller: EditMemeController

    private val viewModel: EditMemeViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private val args: EditMemeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mvcView = InjectionUtils.provideEditMemeMvcView(inflater, container, this)
        controller = InjectionUtils.provideEditMemeController(viewModel, args.memeId, this)
        controller.bindView(mvcView)

        controller.fetchMeme()

        return mvcView.getAndroidView()
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }
}