package com.hfad.stopwatch.model

/***
 * sealed class определяющий  состояние для секундомера:
 * секундомер работает и секундомер на паузе.
 */
sealed class StopwatchState {

    data class Paused(
        val elapsedTime: Long,
    ) : StopwatchState()

    data class Running(
        val startTime: Long,
        val elapsedTime: Long,
    ) : StopwatchState()


}