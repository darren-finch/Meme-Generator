package com.darrenfinch.memegenerator.screens.common

abstract class BaseMvcView<T> : MvcView<T> {
    //Not concurrent, so be aware of that.
    private val listeners = mutableSetOf<T>()

    override fun registerEventListener(listener: T) {
        listeners.add(listener)
    }
    override fun unregisterEventListener(listener: T) {
        listeners.remove(listener)
    }
    protected fun getListeners() = listeners.toList()
}