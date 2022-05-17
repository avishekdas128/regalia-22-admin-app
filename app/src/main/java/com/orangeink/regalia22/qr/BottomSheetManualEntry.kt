package com.orangeink.regalia22.qr

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orangeink.regalia22.R
import com.orangeink.regalia22.databinding.BottomsheetManualEntryBinding
import com.orangeink.regalia22.util.constants.Constants
import com.orangeink.regalia22.util.hideKeyboard
import com.orangeink.regalia22.util.pxToDp
import com.orangeink.regalia22.util.showKeyboard
import com.orangeink.regalia22.verification.VerificationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetManualEntry : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetManualEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.customBottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { mDialog: DialogInterface ->
            val d = mDialog as BottomSheetDialog
            val containerLayout =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.container)
            val parent = binding.rlBottom.parent as ViewGroup
            parent.removeView(binding.rlBottom)
            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                requireContext().pxToDp(48f)
            )
            layoutParams.gravity = Gravity.BOTTOM
            containerLayout?.addView(binding.rlBottom, containerLayout.childCount)
            val bottomSheet =
                d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.isDraggable = false
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetManualEntryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
    }

    private fun initViews() {
        isCancelable = false
        showKeyboard(requireContext(), binding.etId)
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            if (binding.etId.text.toString().isBlank()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_empty_id),
                    Toast.LENGTH_SHORT
                )
                return@setOnClickListener
            }
            hideKeyboard(requireActivity())
            Intent(requireActivity(), VerificationActivity::class.java).apply {
                putExtra(Constants.SCAN_UNIQUE_ID, binding.etId.text.toString())
                startActivity(this)
                dismiss()
            }
        }
    }
}