package com.prabhat.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prabhat.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "ALEXA"

    lateinit var parentJob: Job

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

    suspend fun work(i: Int) {

        delay(3000)
        Log.d(TAG, "work: $i done ${Thread.currentThread().name}")
    }
    val handler = CoroutineExceptionHandler{_,exception->
        Log.d(TAG, "Exception thrown in one of the children $exception: ")
    }

/*    private fun main() {

        parentJob = CoroutineScope(Main).launch {

            //------JOBA------
            val jobA=launch {
                val resultA=getResult(1)
                Log.d(TAG, "main: $resultA")
            }
            jobA.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting result ${throwable} ")
                }
            }

            //------JOBB------
            val jobB=launch {
                val resultB=getResult(2)
                Log.d(TAG, "main: $resultB")
            }
            jobB.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting result ${throwable} ")
                }
            }

            //------JOBC------
            val jobC=launch {
                val resultC=getResult(3)
                Log.d(TAG, "main: $resultC")
            }
            jobC.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting result ${throwable} ")
                }
            }
        }

    }*/

    //scenario 1
/*private fun main() {

    parentJob = CoroutineScope(Main).launch(handler) {

        //------JOBA------
        val jobA=launch {
            val resultA=getResult(1)
            Log.d(TAG, "main: $resultA")
        }
        jobA.invokeOnCompletion {throwable->
            if(throwable!=null){

                Log.d(TAG, "Error getting result ${throwable} ")
            }
        }

        //------JOBB------
        val jobB=launch {
            val resultB=getResult(2)
            Log.d(TAG, "main: $resultB")
        }
        jobB.invokeOnCompletion {throwable->
            if(throwable!=null){

                Log.d(TAG, "Error getting result ${throwable} ")
            }
        }

        //------JOBC------
        val jobC=launch {
            val resultC=getResult(3)
            Log.d(TAG, "main: $resultC")
        }
        jobC.invokeOnCompletion {throwable->
            if(throwable!=null){

                Log.d(TAG, "Error getting result ${throwable} ")
            }
        }
    }
    parentJob.invokeOnCompletion {throwable->
        if (throwable!=null){
            Log.d(TAG, "Parent job failed ${throwable}")
        }else{
            Log.d(TAG, "Parent job success")
        }

    }

}*/
  /*  private suspend fun getResult(number: Int): Int {

        delay(number * 500L)
        if (number==2){
            throw Exception("Error getting result")
        }
        return number * 2


    }*/
//scenario 2
/*    private fun main() {

        parentJob = CoroutineScope(Main).launch(handler) {

            //------JOBA------
            val jobA=launch {
                val resultA=getResult(1)
                Log.d(TAG, "resultA: $resultA")
            }
            jobA.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultA ${throwable} ")
                }
            }

            //------JOBB------
            val jobB=launch {
                val resultB=getResult(2)
                Log.d(TAG, "resultB: $resultB")
            }
            delay(200)
            jobB.cancel()
            jobB.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultB ${throwable} ")
                }
            }

            //------JOBC------
            val jobC=launch {
                val resultC=getResult(3)
                Log.d(TAG, "resultC: $resultC")
            }
            jobC.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultC ${throwable} ")
                }
            }
        }
        parentJob.invokeOnCompletion {throwable->
            if (throwable!=null){
                Log.d(TAG, "Parent job failed ${throwable}")
            }else{
                Log.d(TAG, "Parent job success")
            }

        }

    }

    private suspend fun getResult(number: Int): Int {

        delay(number * 500L)
        if (number==2){
//            throw Exception("Error getting result")
            cancel(CancellationException("Error getting result for ${number}"))
        }
        return number * 2


    }*/


    //what if cancellation exception is thrown
    private fun main() {

        parentJob = CoroutineScope(Main).launch(handler) {

            //------JOBA------
            val jobA=launch {
                val resultA=getResult(1)
                Log.d(TAG, "resultA: $resultA")
            }
            jobA.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultA ${throwable} ")
                }
            }

            //------JOBB------
            val jobB=launch {
                val resultB=getResult(2)
                Log.d(TAG, "resultB: $resultB")
            }
            delay(200)
            jobB.cancel()
            jobB.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultB ${throwable} ")
                }
            }

            //------JOBC------
            val jobC=launch {
                val resultC=getResult(3)
                Log.d(TAG, "resultC: $resultC")
            }
            jobC.invokeOnCompletion {throwable->
                if(throwable!=null){

                    Log.d(TAG, "Error getting resultC ${throwable} ")
                }
            }
        }
        parentJob.invokeOnCompletion {throwable->
            if (throwable!=null){
                Log.d(TAG, "Parent job failed ${throwable}")
            }else{
                Log.d(TAG, "Parent job success")
            }

        }

    }

    private suspend fun getResult(number: Int): Int {

        delay(number * 500L)
        if (number==2){
//            throw Exception("Error getting result")
//            cancel(CancellationException("Error getting result for ${number}"))
          throw CancellationException("Error getting result for ${number}")
        }
        return number * 2


    }
}