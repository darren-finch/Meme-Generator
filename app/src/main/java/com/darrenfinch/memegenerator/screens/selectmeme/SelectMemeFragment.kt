package com.darrenfinch.memegenerator.screens.selectmeme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.darrenfinch.memegenerator.R
import com.darrenfinch.memegenerator.adapters.MemeRecyclerViewAdapter
import com.darrenfinch.memegenerator.adapters.MemeViewHolder
import com.darrenfinch.memegenerator.databinding.FragmentSelectMemeBinding
import com.darrenfinch.memegenerator.misc.MarginItemDecoration
import com.darrenfinch.memegenerator.util.InjectionUtils
import kotlinx.android.synthetic.main.fragment_select_meme.*

class SelectMemeFragment : Fragment() {

    private lateinit var controller: SelectMemeController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mvcView = InjectionUtils.provideSelectMemeMvcView(requireContext(), inflater, container)

        controller = InjectionUtils.provideSelectMemeController(this)
        controller.bindToView(mvcView)

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