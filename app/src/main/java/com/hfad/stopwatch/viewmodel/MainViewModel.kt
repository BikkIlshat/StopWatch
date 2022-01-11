package com.hfad.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import com.hfad.stopwatch.viewmodel.interaction.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
) : ViewModel() {
    private val scope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )
    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
       startJob()
        stopwatchStateHolder.start()
    }



    private fun startJob() {
        job?.cancel()
        job = scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(20)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIME
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }

    companion object {
        private const val DEFAULT_TIME = "00:00:000"
    }
}