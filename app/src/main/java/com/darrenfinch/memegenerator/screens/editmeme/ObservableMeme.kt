package com.darrenfinch.memegenerator.screens.editmeme

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.darrenfinch.memegenerator.BR
import com.darrenfinch.memegenerator.models.MemePicture

class ObservableMeme : BaseObservable() {
    private var id = 0
    private var topTextData = ""
    private var bottomTextData = ""
    private var imageUrl = ""

    fun getImageUrl() = imageUrl

    @Bindable
    fun getTopText() : String {
        return topTextData
    }

    fun setTopText(value: String) {
        if(topTextData != value) {
            topTextData = value
            notifyPropertyChanged(BR.topText)
        }
    }

    @Bindable
    fun getBottomText() : String {
        return bottomTextData
    }

    fun setBottomText(value: String) {
        if(bottomTextData != value) {
            bottomTextData = value
            notifyPropertyChanged(BR.bottomText)
        }
    }

    fun set(memePicture: MemePicture) {
        id = memePicture.id
        imageUrl = memePicture.url
    }

    fun get() : MemePicture = MemePicture(
        id = id,
        url = imageUrl
    )
}