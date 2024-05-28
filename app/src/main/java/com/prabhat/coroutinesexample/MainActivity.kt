package com.prabhat.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prabhat.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "ALEXA"

    var count=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        main()
        binding.button.setOnClickListener {

           binding.text.text=count++.toString()
        }


    }

    private fun main() {
        CoroutineScope(Main).launch {//parent job
            Log.d(TAG, "main: ${Thread.currentThread().name}")
            //it is delaying a isolated coroutine in that thread not the whole thread
//            delay(3000)
            //Thread sleep will sleep whole the thread
//            Thread.sleep(5000)

            for (i in 1..100_000){
                launch {//children job

                    doNetworkRequest()
                }
            }
        }
    }
    private suspend fun doNetworkRequest(){
        Log.d(TAG, "Starting Network Request: ")
        delay(3000)
        Log.d(TAG, "Finished Network Request: ")
    }


}