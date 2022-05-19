package com.orangeink.regalia22.generate.free.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orangeink.regalia22.databinding.FragmentAlumniBinding
import com.orangeink.regalia22.generate.free.viewmodel.FreePassViewModel
import com.orangeink.regalia22.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlumniFragment : Fragment() {

    private lateinit var binding: FragmentAlumniBinding
    private val viewModel: FreePassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlumniBinding.inflate(inflater)
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
            if (binding.etYear.text.isNullOrBlank()) {
                Toast.makeText(
                    requireActivity(),
                    "Year of passing cannot be empty!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (binding.etPhone.text.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "Phone cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (binding.etEmail.text.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "Email cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (binding.etDepartment.text.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "Department cannot be empty!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            binding.progressGeneratePass.visibility = View.VISIBLE
            hideKeyboard(requireActivity())
            viewModel.alumniPass(
                binding.etName.text.toString(),
                binding.etYear.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPhone.text.toString(),
                binding.etDepartment.text.toString()
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