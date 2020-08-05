package com.darrenfinch.memegenerator.screens.selectmeme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.darrenfinch.memegenerator.R
import com.darrenfinch.memegenerator.adapters.MemeRecyclerViewAdapter
import com.darrenfinch.memegenerator.adapters.MemeViewHolder
import com.darrenfinch.memegenerator.databinding.FragmentSelectMemeBinding
import com.darrenfinch.memegenerator.misc.MarginItemDecoration
import com.darrenfinch.memegenerator.models.MemePicture
import com.darrenfinch.memegenerator.screens.common.BaseMvcView

class SelectMemeMvcViewImpl(private val context: Context,
                            inflater: LayoutInflater,
                            container: ViewGroup?) : SelectMemeMvcView, BaseMvcView<SelectMemeMvcView.EventListener>() {

    private val adapter = MemeRecyclerViewAdapter(mutableListOf(), object : MemeViewHolder.Listener {
        override fun onClick(memeId: Int) {
            for(listener in getListeners()) {
                listener.onMemeClick(memeId)
            }
        }
    })

    private val binding = DataBindingUtil.inflate<FragmentSelectMemeBinding>(inflater, R.layout.fragment_select_meme, container, false).apply {
        memesRecyclerView.adapter = adapter
        memesRecyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        memesRecyclerView.addItemDecoration(MarginItemDecoration(16))

        refreshButton.setOnClickListener {
            onRefreshClick()
        }
    }

    private fun onRefreshClick() {
        for(listener in getListeners()) {
            listener.onRefreshClick()
        }
    }

    override fun bindMemes(memes: List<MemePicture>) {
        adapter.updateMemes(memes)
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun getAndroidView() : View {
        return binding.root
    }
}