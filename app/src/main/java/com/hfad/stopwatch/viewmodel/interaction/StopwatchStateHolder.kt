package com.hfad.stopwatch.viewmodel.interaction

import com.hfad.stopwatch.model.StopwatchState
import com.hfad.stopwatch.utils.ElapsedTimeCalculator
import com.hfad.stopwatch.utils.TimestampMillisecondsFormatter

/***
 * Управление состоянием секундомера
 */
class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter,
) {

    var currentState: StopwatchState = StopwatchState.Paused(0)
        private set

    fun start() {
        currentState = stopwatchStateCalculator
            .calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator
            .calculatePausedState(currentState)
    }

    fun stop() {
        currentState = StopwatchState.Paused(0)
    }

    fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState,
                stopwatchStateCalculator.getCurrentTime()
            )
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}