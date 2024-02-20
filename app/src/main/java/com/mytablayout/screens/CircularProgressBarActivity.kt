package com.mytablayout.screens

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mytablayout.R
import com.mytablayout.databinding.ActivityCircularProgressBarBinding
import com.mytablayout.modals.MFSModal
import com.mytablayout.modals.TotalPriceData

class CircularProgressBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCircularProgressBarBinding

    private val totalTimeInMillis: Long = 60000 // 20 seconds
    private val intervalInMillis: Long = 1000 // 1 second
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCircularProgressBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {

        val list = ArrayList<TotalPriceData>()
        list.add(TotalPriceData("Total Saldo", "Rp10.999.000"))
        list.add(TotalPriceData("Limit Tersedia", "Rp0"))
        list.add(TotalPriceData("Value 3", "Rp1.000.000"))

        val data = MFSModal(
            R.drawable.popcorn_unselected,
            "Dummy Title",
            "Pay now, pay later and get instant cash this is sample of two line",
            "Overdue - Payment due date: 3 June 2023",
            list
        )

        binding.ccvMFS.setData(data)

        val progressAnimator = ValueAnimator.ofFloat(100f, 0f)
        progressAnimator.duration = totalTimeInMillis + 1000
        progressAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            binding.progressBar.progress = progress
        }

        progressAnimator.start()

        startTimer()

    }

    private fun startTimer() {

        binding.progressBar.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer(totalTimeInMillis, intervalInMillis) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                binding.timerTextView.text = String.format("%d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Explicitly set the progress to 0.0 when the countdown is finished
                binding.progressBar.progress = 0.0f
                binding.timerTextView.text = "0:00"

                binding.progressBar.visibility = View.GONE
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}