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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.random.Random
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

   /* private fun main() {
      CoroutineScope(Main).launch {
          Log.d(TAG, "main: Starting job in ${Thread.currentThread().name}")
          val result1=getResult()
          Log.d(TAG, "result1: $result1")
          val result2=getResult()
          Log.d(TAG, "result1: $result2")
          val result3=getResult()
          Log.d(TAG, "result1: $result3")
          val result4=getResult()
          Log.d(TAG, "result1: $result4")
          val result5=getResult()
          Log.d(TAG, "result1: $result5")
      }
    }*/
   private fun main() {
       CoroutineScope(IO).launch {
           Log.d(TAG, "main: Starting job in ${Thread.currentThread().name}")
           val result1=getResult()
           Log.d(TAG, "IO-> result1: $result1")
           val result2=getResult()
           Log.d(TAG, "IO-> result1: $result2")
           val result3=getResult()
           Log.d(TAG, "IO-> result1: $result3")
           val result4=getResult()
           Log.d(TAG, "IO-> result1: $result4")
           val result5=getResult()
           Log.d(TAG, "IO-> result1: $result5")
       }
       CoroutineScope(Main).launch {
           Log.d(TAG, "main: Starting job in ${Thread.currentThread().name}")
           val result1=getResult()
           Log.d(TAG, "result1: $result1")
           val result2=getResult()
           Log.d(TAG, "result1: $result2")
           val result3=getResult()
           Log.d(TAG, "result1: $result3")
           val result4=getResult()
           Log.d(TAG, "result1: $result4")
           val result5=getResult()
           Log.d(TAG, "result1: $result5")
       }

       CoroutineScope(Main).launch {
           delay(1000)
           runBlocking {
               Log.d(TAG, "Blocking thread: ${Thread.currentThread().name}")
               delay(4000)

               Log.d(TAG, "Done blocking thread: ${Thread.currentThread().name}")

           }
       }
   }
    private suspend fun getResult():Int{
        delay(1000)
        return Random.nextInt(0,100)

    }
    private suspend fun doNetworkRequest(){
        Log.d(TAG, "Starting Network Request: ")
        delay(3000)
        Log.d(TAG, "Finished Network Request: ")
    }


}