package com.orangeink.regalia22.generate.pass

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orangeink.regalia22.databinding.ActivityGeneratePassBinding
import com.orangeink.regalia22.generate.pass.viewmodel.GeneratePassViewModel
import com.orangeink.regalia22.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneratePassBinding
    private val viewModel: GeneratePassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        subscribeToLiveData()
    }

    private fun setListeners() {
        binding.btnSubmit.setOnClickListener {
            if (binding.etName.text.isNullOrBlank()) {
                Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etRoll.text.isNullOrBlank()) {
                Toast.makeText(this, "Roll Number cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etEmail.text.isNullOrBlank()) {
                Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etAllowed.text.isNullOrBlank()) {
                Toast.makeText(this, "Allowed cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var amount = ""
            if (binding.etAmount.text.isNullOrBlank())
                amount = "FREE"
            hideKeyboard(this)
            binding.progressGeneratePass.visibility = View.VISIBLE
            viewModel.generatePass(
                binding.etName.text.toString(),
                binding.etRoll.text.toString(),
                binding.etEmail.text.toString(),
                binding.etAllowed.text.toString().toInt(),
                amount
            )
        }
    }

    private fun subscribeToLiveData() {
        viewModel.generate.observe(this) {
            binding.progressGeneratePass.visibility = View.GONE
            Toast.makeText(this, "Successfully generated!", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.error.observe(this) {
            binding.progressGeneratePass.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}