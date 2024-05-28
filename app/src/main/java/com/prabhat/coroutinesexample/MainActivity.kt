package com.prabhat.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prabhat.coroutinesexample.databinding.ActivityMainBinding
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

    private val PROGRESS_MAX = 100
    private val PROGRESS_START = 0
    private val JOB_TIME = 4000
    private lateinit var job: CompletableJob

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
        binding.button.setOnClickListener {
            setNewText("Clicked")
            CoroutineScope(IO).launch {

                fakeApiRequest()
            }
        }


    }

    //using launch
/*    private suspend fun fakeApiRequest() {

        val startTime=System.currentTimeMillis()
        val parentJob=CoroutineScope(IO).launch {
            val job1 = launch {
                val time1= measureTimeMillis {

                    Log.d(TAG, "fakeApiRequest: launching job1 in thread ${Thread.currentThread().name}")
                    val result1=getResult1FromApi()
                    setTextOnMainThread("Got $result1")
                }
                Log.d(TAG, "fakeApiRequest: completed job1 in $time1 ms.")
            }
            val job2 = launch {
                val time2= measureTimeMillis {

                    Log.d(TAG, "fakeApiRequest: launching job2 in thread ${Thread.currentThread().name}")
                    val result2=getResult2FromApi()
                    setTextOnMainThread("Got $result2")
                }
                Log.d(TAG, "fakeApiRequest: completed job2 in $time2 ms.")
            }

        }
        parentJob.invokeOnCompletion {
            Log.d(TAG, "fakeApiRequest: total elapsed time ${System.currentTimeMillis()-startTime}")
        }
    }*/

    //using async await
    private suspend fun fakeApiRequest(){
        CoroutineScope(IO).launch {
            val executionTime= measureTimeMillis {
                val result1=async{
                    Log.d(TAG, "fakeApiRequest: launching job1 on ${Thread.currentThread().name}")
                    getResult1FromApi()
                }
                val result2=async{
                    Log.d(TAG, "fakeApiRequest: launching job2 on ${Thread.currentThread().name}")
                    getResult2FromApi()
                }
                setTextOnMainThread("Got ${result1.await()}")
                setTextOnMainThread("Got ${result2.await()}")
            }
            Log.d(TAG, "fakeApiRequest: total time elapsed ${executionTime}")
        }
    }
    private fun setNewText(input: String) {
        // Get current text safely
        val currentText = binding.text?.text.toString()

        // Append new text with a newline
        val newText = "$currentText\n$input"

        // Set the new text
        binding.text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }

    }

    private suspend fun getResult1FromApi(): String {

        delay(1000)
        return "Result #1"
    }

    private suspend fun getResult2FromApi(): String {

        delay(1700)
        return "Result #2"
    }


}