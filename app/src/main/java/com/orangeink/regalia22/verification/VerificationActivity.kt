package com.orangeink.regalia22.verification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orangeink.regalia22.R
import com.orangeink.regalia22.databinding.ActivityVerificationBinding
import com.orangeink.regalia22.util.constants.Constants
import com.orangeink.regalia22.verification.data.model.Pass
import com.orangeink.regalia22.verification.viewmodel.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private lateinit var uniqueId: String

    private val viewModel: VerificationViewModel by viewModels()

    private var tempCount = 0
    private var maxCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initViews()
        setListeners()
        subscribeToLiveData()
    }

    private fun getIntentData() {
        intent?.let {
            uniqueId = it.getStringExtra(Constants.SCAN_UNIQUE_ID) ?: ""
        }
    }

    private fun initViews() {
        viewModel.scan(uniqueId)
    }

    private fun setListeners() {
        binding.ivAdd.setOnClickListener {
            tempCount++
            if (tempCount > maxCount)
                tempCount = maxCount
            binding.tvCount.text = "$tempCount"
        }
        binding.ivRemove.setOnClickListener {
            tempCount--
            if (tempCount < 1)
                tempCount = 1
            binding.tvCount.text = "$tempCount"
        }
        binding.btnSubmit.setOnClickListener {
            binding.progressVerification.visibility = View.VISIBLE
            viewModel.verify(uniqueId, tempCount)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.pass.observe(this) {
            binding.progressVerification.visibility = View.GONE
            when (it.date) {
                "18" -> {
                    if (it.dayOneValidity.isNotEmpty() && (it.allowed == it.countDayOne))
                        invalidPass()
                    else
                        setupUI(it, it.countDayOne)
                }
                "20" -> {
                    if (it.dayTwoValidity.isNotEmpty() && (it.allowed == it.countDayTwo))
                        invalidPass()
                    else
                        setupUI(it, it.countDayTwo)
                }
            }
        }
        viewModel.verify.observe(this) {
            binding.progressVerification.visibility = View.GONE
            Toast.makeText(this, getString(R.string.entry_success), Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            binding.progressVerification.visibility = View.GONE
            finish()
        }
    }

    private fun setupUI(data: Pass, count: Int) {
        binding.tvName.text = data.name?.uppercase()
        binding.tvEmail.text = data.email?.lowercase()
        binding.tvRoll.text = data.rollNumber?.uppercase()
        val originalCount = data.allowed - count
        binding.tvAllowed.text = "$originalCount allowed"
        binding.tvCount.text = "$originalCount"
        tempCount = originalCount
        maxCount = data.allowed
    }

    private fun invalidPass() {
        Toast.makeText(this, getString(R.string.invalid_pass), Toast.LENGTH_SHORT).show()
        finish()
    }
}