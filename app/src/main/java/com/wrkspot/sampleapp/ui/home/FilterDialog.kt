package com.wrkspot.sampleapp.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wrkspot.sampleapp.databinding.FilterDialogBinding
import com.wrkspot.sampleapp.utils.Population

class FilterDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FilterDialogBinding

    private var filterClickListener: FilterClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setFilterClickListener(filterClickListener: FilterClickListener) {
        this.filterClickListener = filterClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lessThanMillion.setOnClickListener {
            filterClickListener?.onFilterCLicked(Population.LessThanOneMillion)
            dismiss()
        }
        binding.lessThanFiveMillion.setOnClickListener {
            filterClickListener?.onFilterCLicked(Population.LessThanFiveMillion)
            dismiss()
        }
        binding.lessThanTenMillion.setOnClickListener {
            filterClickListener?.onFilterCLicked(Population.LessThanTenMillion)
            dismiss()
        }
        binding.clearAllFilter.setOnClickListener {
            filterClickListener?.onFilterCLicked(Population.None)
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // used to show the bottom sheet dialog
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val TAG = "FilterDialog"
    }

    interface FilterClickListener {
        fun onFilterCLicked(population: Population)
    }
}