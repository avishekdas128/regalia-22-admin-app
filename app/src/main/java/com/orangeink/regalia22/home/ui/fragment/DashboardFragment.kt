package com.orangeink.regalia22.home.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.orangeink.regalia22.R
import com.orangeink.regalia22.databinding.FragmentDashboardBinding
import com.orangeink.regalia22.generate.free.ui.FreeEntryActivity
import com.orangeink.regalia22.generate.pass.GeneratePassActivity
import com.orangeink.regalia22.home.viewmodel.DashboardViewModel
import com.orangeink.regalia22.preferences.Prefs
import com.orangeink.regalia22.qr.BottomSheetManualEntry
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListeners()
        subscribeToLiveData()
    }

    private fun initViews() {
        setupCountDown()
        viewModel.dashboard()
    }

    private fun subscribeToLiveData() {
        viewModel.home.observe(viewLifecycleOwner) {
            binding.progressHome.visibility = View.GONE
            binding.swHome.isRefreshing = false

            binding.tvDayLabel.text = it.label?.uppercase()
            binding.tvTotalCount.text = it.total.toString()
            binding.tvFreeEntryCount.text = "No Pass Entry Count = ${it.unpaidPassTotal}"

            val data = arrayListOf(
                it.categorized.cseCount,
                it.categorized.eceCount,
                it.categorized.itCount,
                it.categorized.eeCount,
                it.categorized.aeieCount
            )
            setupChart(data)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.progressHome.visibility = View.GONE
            binding.swHome.isRefreshing = false
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        binding.swHome.setOnRefreshListener { viewModel.dashboard() }
        binding.tvFreeEntry.setOnClickListener {
            Intent(requireActivity(), FreeEntryActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.tvGeneratePass.setOnClickListener {
            if (Prefs(requireActivity()).user?.role == "Admin") {
                Intent(requireActivity(), GeneratePassActivity::class.java).apply {
                    startActivity(this)
                }
            } else Toast.makeText(
                requireActivity(),
                getString(R.string.no_access),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.tvManualEntry.setOnClickListener {
            val bottomSheet = BottomSheetManualEntry()
            bottomSheet.show(childFragmentManager, BottomSheetManualEntry::class.java.name)
        }
    }

    private fun setupCountDown() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.parse(getString(R.string.event_start_date))?.time?.let {
            val timeLeft = it - Calendar.getInstance().timeInMillis
            if (timeLeft < 0) {
                binding.tvCountdown.visibility = View.GONE
                binding.countdownTimer.visibility = View.GONE
            } else binding.countdownTimer.start(timeLeft)
        }
        binding.countdownTimer.setOnCountdownEndListener {
            binding.tvCountdown.visibility = View.GONE
            binding.countdownTimer.visibility = View.GONE
        }
    }


    private fun setupChart(data: ArrayList<Int>) {
        val pieChart = binding.chart
        val entries = ArrayList<PieEntry>()
        val label = "Department"

        //initializing data
        data.forEachIndexed { index, i ->
            entries.add(PieEntry(i.toFloat(), index))
        }

        val pieColors = arrayListOf(
            Color.parseColor("#003f5c"),
            Color.parseColor("#58508d"),
            Color.parseColor("#bc5090"),
            Color.parseColor("#ff6361"),
            Color.parseColor("#ffa600")
        )
        //collecting the entries with label name
        val pieDataSet = PieDataSet(entries, label)
        //setting text size of the value
        pieDataSet.sliceSpace = 5f
        pieDataSet.selectionShift = 5f
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = pieColors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false)
        pieChart.data = pieData
        pieChart.clipToPadding = false
        pieChart.invalidate()
        pieChart.centerText = "${data[0]}"
        pieChart.setCenterTextSize(12f)
        pieChart.setCenterTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        pieChart.setDrawEntryLabels(false)
        pieChart.isRotationEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setEntryLabelTextSize(12f)
        pieChart.holeRadius = 75f
        pieChart.setHoleColor(
            ContextCompat.getColor(
                requireActivity(),
                android.R.color.transparent
            )
        )
        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Timber.i(h?.x.toString())
                pieChart.centerText = "${data[h?.x?.toInt()!!]}"
            }

            override fun onNothingSelected() {
                //Not Required
            }
        })
        pieChart.highlightValue(0f, 0)
        //legend attributes
        pieChart.legend.isEnabled = false
    }

}