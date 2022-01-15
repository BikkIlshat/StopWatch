package com.hfad.stopwatch.model

import java.lang.System.currentTimeMillis

class RepositoryImpl : Repository {
    override fun getMilliseconds(): Long = currentTimeMillis()
}