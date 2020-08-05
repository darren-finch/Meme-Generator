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
    private lateinit var binding: FragmentSelectMemeBinding

    private val viewModel: SelectMemeViewModel by viewModels {
        InjectionUtils.provideSelectMemeViewModelFactory()
    }

    private val adapter = MemeRecyclerViewAdapter(mutableListOf(), object : MemeViewHolder.Listener {
        override fun onClick(memeId: Int) {
            findNavController().navigate(SelectMemeFragmentDirections.actionSelectMemeFragmentToEditMemeFragment(memeId))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSelectMemeBinding>(inflater, R.layout.fragment_select_meme, container, false).apply {
            memesRecyclerView.adapter = adapter
            memesRecyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            memesRecyclerView.addItemDecoration(MarginItemDecoration(16))

            refreshButton.setOnClickListener {
                fetchMeme()
            }
        }

        fetchMeme()

        return binding.root
    }

    private fun fetchMeme() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getRandomMemePictures().observe(viewLifecycleOwner, Observer { memePictures ->
            if(memePictures == null) {
                Toast.makeText(context, "Couldn't get memes. Check your internet connection.", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.progressBar.visibility = View.GONE
                adapter.updateMemes(memePictures)
            }
        })
    }
}