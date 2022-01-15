package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hfad.stopwatch.databinding.ActivityMainBinding
import com.hfad.stopwatch.model.RepositoryImpl
import com.hfad.stopwatch.utils.ElapsedTimeCalculator
import com.hfad.stopwatch.utils.TimestampMillisecondsFormatter
import com.hfad.stopwatch.viewmodel.MainViewModel
import com.hfad.stopwatch.viewmodel.interaction.StopwatchStateCalculator
import com.hfad.stopwatch.viewmodel.interaction.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by viewBinding(CreateMethod.INFLATE)
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var job: Job? = null

    private val mainViewModelFirstwatcher by lazy {
        MainViewModel(
            StopwatchStateHolder(
                StopwatchStateCalculator(
                    RepositoryImpl(),
                    ElapsedTimeCalculator),
                ElapsedTimeCalculator,
                TimestampMillisecondsFormatter
            )
        )
    }
    private val mainViewModelSecondwatcher by lazy {
        MainViewModel(
            StopwatchStateHolder(
                StopwatchStateCalculator(
                    RepositoryImpl(),
                    ElapsedTimeCalculator
                ),
                ElapsedTimeCalculator,
                TimestampMillisecondsFormatter
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        with(viewBinding) {
            job = scope.launch {
                mainViewModelFirstwatcher
                    .ticker
                    .collect {
                        textTime.text = it
                    }
            }
            job = scope.launch {
                mainViewModelSecondwatcher
                    .ticker
                    .collect {
                        textSecondTime.text = it
                    }
            }
            buttonStart.setOnClickListener {
                mainViewModelFirstwatcher.start()
            }
            buttonPause.setOnClickListener {
                mainViewModelFirstwatcher.pause()
            }
            buttonStop.setOnClickListener {
                mainViewModelFirstwatcher.stop()
            }
            buttonSecondStart.setOnClickListener {
                mainViewModelSecondwatcher.start()
            }
            buttonSecondPause.setOnClickListener {
                mainViewModelSecondwatcher.pause()
            }
            buttonSecondStop.setOnClickListener {
                mainViewModelSecondwatcher.stop()
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}



























