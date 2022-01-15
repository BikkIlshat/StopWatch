package com.hfad.stopwatch.model

/***
 * Интерфейс возвращающий текущее время (в миллисекундах).
 */
interface Repository {
    fun getMilliseconds(): Long
}