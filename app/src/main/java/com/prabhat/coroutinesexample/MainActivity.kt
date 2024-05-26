package com.prabhat.coroutinesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prabhat.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private val RESULT_1="Result #1"
    private val RESULT_2="Result #2"
    private val TAG="ALEXA"
//    private val JOB_TIMEOUT=1900L;
    private val JOB_TIMEOUT=2100L;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btn.setOnClickListener {


//            io,main and default
            CoroutineScope(Dispatchers.IO).launch{
                fakeApiRequest()
            }

        }}
        private fun setNewText(input: String) {
            val currentText = binding.textView.text.toString()
            val newText = "$currentText\n$input"
            binding.textView.text = newText
        }

        private suspend fun setTextOnMainThread(input: String){
        withContext(Dispatchers.Main){
            setNewText(input)
        }

    }


    private suspend fun fakeApiRequest(){

        withContext(Dispatchers.IO){

          /*  val job=launch {
                val result1=getResultFrom1Api()
                setTextOnMainThread("Got ${result1}")
                Log.d(TAG, "fakeApiRequest: $result1")

                val result2=getResultFrom2Api()
                setTextOnMainThread("Got ${result2}")
            }*/
            val job= withTimeoutOrNull(JOB_TIMEOUT) {
                val result1=getResultFrom1Api()
                setTextOnMainThread("Got ${result1}")
                Log.d(TAG, "fakeApiRequest: $result1")

                val result2=getResultFrom2Api()
                setTextOnMainThread("Got ${result2}")
            }
            if (job==null){
                val cancelMessage="Cancel Job You job took longer time $JOB_TIMEOUT"
                setTextOnMainThread(cancelMessage)
            }
        }


    }
    private suspend fun getResultFrom1Api():String{

        logThread("getResultFrom1Api")
        delay(1000)
        return RESULT_1
    }
    private suspend fun getResultFrom2Api():String{

        logThread("getResultFrom2Api")
        delay(1000)
        return RESULT_2
    }
    private fun logThread(methodName:String){

        Log.d(TAG, "logThread:${methodName}  ${Thread.currentThread().name} ")


    }
}