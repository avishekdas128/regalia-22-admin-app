package com.orangeink.regalia22.generate.free.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orangeink.regalia22.databinding.FragmentStudentBinding
import com.orangeink.regalia22.generate.free.viewmodel.FreePassViewModel
import com.orangeink.regalia22.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFragment : Fragment() {

    private lateinit var binding: FragmentStudentBinding
    private val viewModel: FreePassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        subscribeToLiveData()
    }

    private fun setListeners() {
        binding.btnSubmit.setOnClickListener {
            if (binding.etName.text.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "Name cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (binding.etRoll.text.isNullOrBlank()) {
                Toast.makeText(
                    requireActivity(),
                    "Roll Number cannot be empty!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (binding.etPhone.text.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "Phone cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            binding.progressGeneratePass.visibility = View.VISIBLE
            hideKeyboard(requireActivity())
            viewModel.studentPass(
                binding.etName.text.toString(),
                binding.etRoll.text.toString(),
                binding.etPhone.text.toString()
            )
        }
    }

    private fun subscribeToLiveData() {
        viewModel.success.observe(viewLifecycleOwner) {
            binding.progressGeneratePass.visibility = View.GONE
            Toast.makeText(requireActivity(), "Successfully submitted!", Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.progressGeneratePass.visibility = View.GONE
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }

}