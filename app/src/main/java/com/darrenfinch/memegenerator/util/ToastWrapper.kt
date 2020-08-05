package com.darrenfinch.memegenerator.util

import android.content.Context
import android.widget.Toast

class ToastWrapper(private val context: Context) {
    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
