package com.example.myapplication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

class Game(val scope: CoroutineScope, val screenW:Int, val screenH: Int) {
    var counter = 0
    val state = MutableStateFlow(0)
    val background = Background(screenW)
    var isPlaying = true
    fun Play(){
        scope.launch {
            //counter = 0
            isPlaying = true
            while (isPlaying) {
                delay(40)
                background.Roll()
                counter++
                state.emit(counter)
            }

        }

    }
}