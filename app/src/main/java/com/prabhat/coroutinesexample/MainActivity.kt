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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
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

    lateinit var parentJob:Job

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

            parentJob.cancel()

        }
    }

    suspend fun work(i:Int){

        delay(3000)
        Log.d(TAG, "work: $i done ${Thread.currentThread().name}")
    }
  /* private fun main() {

       val startTime=System.currentTimeMillis()
       Log.d(TAG, "main: Starting parent job")
       parentJob=CoroutineScope(Main).launch {
           launch {
               work(1)
           }
           launch {
               work(2)
           }
       }
       parentJob.invokeOnCompletion {throwable->
           if (throwable!=null){
               Log.d(TAG, "job was cancelled after ${System.currentTimeMillis()-startTime} ms.")
           }else{
               Log.d(TAG, "Done in ${System.currentTimeMillis()-startTime}  ms.")
           }
       }
   }*/

    /*private fun main() {

        val startTime=System.currentTimeMillis()
        Log.d(TAG, "main: Starting parent job")
        parentJob=CoroutineScope(Main).launch {
            GlobalScope.launch {
                work(1)
            }
            GlobalScope.launch {
                work(2)
            }
        }
        parentJob.invokeOnCompletion {throwable->
            if (throwable!=null){
                Log.d(TAG, "job was cancelled after ${System.currentTimeMillis()-startTime} ms.")
            }else{
                Log.d(TAG, "Done in ${System.currentTimeMillis()-startTime}  ms.")
            }
        }
    }*/
    private fun main() {

        val startTime=System.currentTimeMillis()
        Log.d(TAG, "main: Starting parent job")
        parentJob=CoroutineScope(Main).launch {
            CoroutineScope(Main).launch {
                work(1)
            }
            CoroutineScope(Main).launch {
                work(2)
            }
        }
        parentJob.invokeOnCompletion {throwable->
            if (throwable!=null){
                Log.d(TAG, "job was cancelled after ${System.currentTimeMillis()-startTime} ms.")
            }else{
                Log.d(TAG, "Done in ${System.currentTimeMillis()-startTime}  ms.")
            }
        }
    }



    private suspend fun getResult():Int{
        delay(1000)
        return Random.nextInt(0,100)

    }



}