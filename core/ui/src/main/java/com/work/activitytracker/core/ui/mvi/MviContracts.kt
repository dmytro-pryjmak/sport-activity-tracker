package com.work.activitytracker.core.ui.mvi

interface UiState
interface UiAction
interface UiEffect {
    object None : UiEffect
}

class Event<T>(private val content: T) {
    private var consumed = false
    fun consume(): T? = if (consumed) null else content.also { consumed = true }
}
