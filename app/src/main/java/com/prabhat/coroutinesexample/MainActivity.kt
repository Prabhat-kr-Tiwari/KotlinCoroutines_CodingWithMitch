package com.prabhat.coroutinesexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.prabhat.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val rootView: View = findViewById(android.R.id.content)
        showSnackbar(rootView,"hello")

        binding.jobButton.setOnClickListener {

            //this way we check if lateinit value is intialized
            if (!::job.isInitialized){

                initJob()
            }
            binding.jobProgressBar.startJobOrCancel(job)
        }
        val job2 = CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "onCreate: Job2 STARTED ")
            try {
                delay(5000)  // Simulate a long-running task
            } catch (e: CancellationException) {
                Log.d(TAG, "onCreate: Job2 CANCELLED")
            }
        }

        val job3 = CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "onCreate: Job3 STARTED")
            try {
                delay(5000)  // Simulate a long-running task
            } catch (e: CancellationException) {
                Log.d(TAG, "onCreate: Job3 CANCELLED")
            }
        }
        val scope=CoroutineScope(IO).launch {

            Log.d(TAG, "onCreate: scope STARTED")
            try {
                delay(5000)  // Simulate a long-running task
            } catch (e: CancellationException) {
                Log.d(TAG, "onCreate: scope CANCELLED")
            }

        }
        CoroutineScope(IO).launch {
            Log.d(TAG, "onCreate: scope2 STARTED")
            try {
                delay(5000)  // Simulate a long-running task
            } catch (e: CancellationException) {
                Log.d(TAG, "onCreate: scope2 CANCELLED")
            }
        }

        binding.btnCancelJob.setOnClickListener {

            scope.cancel()
        }

      /*  binding.btnCancelJob.setOnClickListener {
            Log.d(TAG, "onCreate: cancel button pressed")
            if(job2.isActive){

                job2.cancel(CancellationException("job 2 cancelled"))
            }
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                Log.d(TAG, "onCreate: cancel button pressed inside coroutine")
                if (job2.isCancelled) {
                    Log.d(TAG, "onCreate: Job2 is cancelled")
                } else if (job2.isActive) {
                    Log.d(TAG, "onCreate: Job2 is Active")
                }
                if (job3.isCancelled) {
                    Log.d(TAG, "onCreate: Job3 is cancelled")
                } else if (job3.isActive) {
                    Log.d(TAG, "onCreate: Job3 is Active")
                }
            }
        }*/

    }

    fun ProgressBar.startJobOrCancel(job:Job){

        if(this.progress>0){
            Log.d(TAG, "${job} is already active Cancelling ")
            resetJob()
        }else{
            binding.jobButton.text="Cancel job #1"

           /* val scope=CoroutineScope(IO).launch {

            }
            CoroutineScope(IO).launch {  }

            scope.cancel()//this will cancel all the all job with IO scope*/

            CoroutineScope(IO+job).launch {

                Log.d(TAG, "Coroutines $this is activated with job $job ")
                for (i in PROGRESS_START..PROGRESS_MAX){
                    delay((JOB_TIME/PROGRESS_MAX).toLong())
                    this@startJobOrCancel.progress=i
                }
                updateJobCompleteTextView("Job is complete")
            }
        }
        
    }
    private fun updateJobCompleteTextView(input:String){
        CoroutineScope(Main).launch {
            binding.jobCompleteText.text=input
        }
    }


    private fun resetJob() {

        if (job.isActive||job.isCompleted){
            job.cancel(CancellationException("Resetting job"))
        }
        initJob()
    }


    fun initJob() {
        binding.jobButton.text = "Start Job #1"
        updateJobCompleteTextView("")
        job = Job()
        job.invokeOnCompletion {
            it?.message.let {
                var msg = it
                if (msg.isNullOrBlank()) {
                    msg = "Unknown Cancellation error"
                }
                Log.d(TAG, "${job}: was cancelled because $msg ")
                showToast(msg)

            }
        }
        binding.jobProgressBar.max=PROGRESS_MAX
        binding.jobProgressBar.progress=PROGRESS_START
    }

    // Function to show a Snackbar
    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(input: String) {
        GlobalScope.launch(Dispatchers.Main) {

            Toast.makeText(this@MainActivity, input, Toast.LENGTH_SHORT).show()
        }
    }


}