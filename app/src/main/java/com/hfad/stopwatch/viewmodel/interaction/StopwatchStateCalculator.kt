package com.hfad.stopwatch.viewmodel.interaction

import com.hfad.stopwatch.model.Repository
import com.hfad.stopwatch.model.StopwatchState
import com.hfad.stopwatch.utils.ElapsedTimeCalculator

/***
 * Определяет состояние секундомера (Running или Paused)
 */

class StopwatchStateCalculator(
    private val repository: Repository,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = repository.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState, getCurrentTime())
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }

    fun getCurrentTime(): Long = repository.getMilliseconds()
}