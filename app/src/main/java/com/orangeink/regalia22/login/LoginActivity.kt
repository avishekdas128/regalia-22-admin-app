package com.orangeink.regalia22.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orangeink.regalia22.home.HomeActivity
import com.orangeink.regalia22.databinding.ActivityLoginBinding
import com.orangeink.regalia22.login.viewmodel.LoginViewModel
import com.orangeink.regalia22.preferences.Prefs
import com.orangeink.regalia22.util.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        subscribeToLiveData()
    }

    private fun setListeners() {
        binding.tvNext.setOnClickListener { login() }
        binding.etEmail.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                login()
            }
            false
        }
    }

    private fun login() {
        if (binding.etEmail.text.isNullOrBlank()) {
            Toast.makeText(this, "Email cannot be empty.", Toast.LENGTH_SHORT)
            return
        }
        if (!binding.etEmail.text.toString().isValidEmail()) {
            Toast.makeText(this, "Invalid Email.", Toast.LENGTH_SHORT)
            return
        }
        binding.progressLogin.visibility = View.VISIBLE
        viewModel.login(binding.etEmail.text.toString())
    }

    private fun subscribeToLiveData() {
        viewModel.user.observe(this) {
            binding.progressLogin.visibility = View.GONE
            Prefs(this).user = it
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        viewModel.error.observe(this) {
            binding.progressLogin.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
        }
    }
}