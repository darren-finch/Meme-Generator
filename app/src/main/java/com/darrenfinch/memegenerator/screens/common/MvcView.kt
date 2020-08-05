package com.darrenfinch.memegenerator.screens.common

import android.view.View

interface MvcView<T> {
    fun getAndroidView() : View
    fun registerEventListener(listener: T)
    fun unregisterEventListener(listener: T)
}