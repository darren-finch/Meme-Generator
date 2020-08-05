package com.darrenfinch.memegenerator.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.memegenerator.databinding.MemeItemBinding
import com.darrenfinch.memegenerator.models.MemePicture

class MemeViewHolder(private val listener: Listener, itemView: View) : RecyclerView.ViewHolder(itemView) {
    interface Listener {
        fun onClick(memeId: Int)
    }

    private val binding = MemeItemBinding.bind(itemView)
    fun bind(memePicture: MemePicture) {
        binding.meme = memePicture
        binding.memeImage.setOnClickListener {
            listener.onClick(memePicture.id)
        }
    }
}