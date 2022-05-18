package com.orangeink.regalia22.search.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orangeink.regalia22.R
import com.orangeink.regalia22.databinding.FragmentSearchBinding
import com.orangeink.regalia22.search.ui.bottomsheet.ResendEmailBottomSheet
import com.orangeink.regalia22.search.viewmodel.SearchViewModel
import com.orangeink.regalia22.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    private var uid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        subscribeToLiveData()
    }

    fun search(query: String) {
        hideKeyboard(requireActivity())
        binding.progressSearch.visibility = View.VISIBLE
        binding.rlTicket.visibility = View.GONE
        binding.layoutEmpty.root.visibility = View.GONE
        viewModel.search(query)
    }

    private fun setListeners() {
        binding.btnResendEmail.setOnClickListener {
            val bottomSheet = ResendEmailBottomSheet()
            bottomSheet.setData(object : ResendEmailBottomSheet.ResendEmailInterface {
                override fun resendEmail(email: String) {
                    uid?.let {
                        binding.progressSearch.visibility = View.VISIBLE
                        viewModel.resendEmail(email, it)
                    }
                }
            })
            bottomSheet.show(childFragmentManager, ResendEmailBottomSheet::class.java.name)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.pass.observe(viewLifecycleOwner) {
            binding.progressSearch.visibility = View.GONE
            if (it.uniqueId == null) {
                binding.layoutEmpty.root.visibility = View.VISIBLE
                binding.rlTicket.visibility = View.GONE
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                this.uid = it.uniqueId

                binding.tvName.text = it.name?.uppercase()
                binding.tvRoll.text = it.rollNumber?.uppercase()
                binding.tvEmail.text = it.email
                binding.rlTicket.visibility = View.VISIBLE
                binding.layoutEmpty.root.visibility = View.GONE
            }
        }
        viewModel.resend.observe(viewLifecycleOwner) {
            binding.progressSearch.visibility = View.GONE
            Toast.makeText(requireContext(), R.string.resend_email_success, Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.progressSearch.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

}