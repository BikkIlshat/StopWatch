package com.hfad.stopwatch.utils

import com.hfad.stopwatch.model.StopwatchState

/***
 *  ElapsedTimeCalculator - вычисляет прошедшее время
 */
object ElapsedTimeCalculator {
    fun calculate(state: StopwatchState.Running, currentTime: Long): Long {
        val timePassedSinceStart = if (currentTime > state.startTime) {
            currentTime - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}
