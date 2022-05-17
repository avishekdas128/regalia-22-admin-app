package com.orangeink.regalia22.home

import android.Manifest
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.orangeink.regalia22.R
import com.orangeink.regalia22.databinding.ActivityHomeBinding
import com.orangeink.regalia22.login.LoginActivity
import com.orangeink.regalia22.preferences.Prefs
import com.orangeink.regalia22.qr.QRScannerActivity
import com.orangeink.regalia22.search.ui.fragments.SearchFragment
import com.orangeink.regalia22.util.addFragmentFromBottom
import com.orangeink.regalia22.util.hideKeyboard
import com.orangeink.regalia22.util.loadFragment
import com.orangeink.regalia22.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val RC_CAMERA: Int = 1001

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setListeners()
    }

    private fun initViews() {
        val user = Prefs(this).user
        user?.let {
            binding.tvHeading.text =
                String.format("Hello, %s!", it.name?.split(" ")?.get(0) ?: "Guest")
            loadFragment(supportFragmentManager, R.id.content_fragment, DashboardFragment())
        }
    }

    private fun setListeners() {
        binding.fabScan.setOnClickListener { checkPermission() }
        binding.ivSearch.setOnClickListener { openSearch() }
        binding.ivCloseSearch.setOnClickListener { closeSearch() }
        binding.ivLogout.setOnClickListener {
            Prefs(this).logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun openSearch() {
        binding.searchInputText.setText("")
        binding.searchInputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val fragment = supportFragmentManager.findFragmentById(R.id.content_fragment)
                if (fragment is SearchFragment)
                    fragment.search(binding.searchInputText.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
        binding.searchOpenView.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.ivSearch.right + binding.ivSearch.left) / 2,
            (binding.ivSearch.top + binding.ivSearch.bottom) / 2,
            0f, binding.rootFrameLayout.width.toFloat()
        )
        circularReveal.duration = 200
        circularReveal.start()
        addFragmentFromBottom(
            supportFragmentManager,
            R.id.content_fragment,
            SearchFragment()
        )
        showKeyboard(this, binding.searchInputText)
    }

    private fun closeSearch() {
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            binding.searchOpenView,
            (binding.ivSearch.right + binding.ivSearch.left) / 2,
            (binding.ivSearch.top + binding.ivSearch.bottom) / 2,
            binding.rootFrameLayout.width.toFloat(), 0f
        )
        circularConceal.duration = 200
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                hideKeyboard(this@HomeActivity)
                binding.searchOpenView.visibility = View.INVISIBLE
                binding.searchInputText.setText("")
                supportFragmentManager.popBackStack()
                circularConceal.removeAllListeners()
            }
        })
    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun checkPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            startActivity(Intent(this, QRScannerActivity::class.java))
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.camera),
                RC_CAMERA,
                Manifest.permission.CAMERA
            )
        }
    }
}