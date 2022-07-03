package com.murat.dailysmoking.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.murat.dailysmoking.R
import com.murat.dailysmoking.databinding.LayoutCreateSmokePriceDialogBinding
import java.util.*

/**
 * Created by Murat
 */
class SmokePriceDialog(
    val updateAction: ((price: String, currency: String) -> Unit)?
) :
    DialogFragment() {

    private var _binding: LayoutCreateSmokePriceDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.update_price_round)

        _binding = LayoutCreateSmokePriceDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        initUi()
    }

    private fun initUi() {
        binding.updateBtn.setOnClickListener {
            updateAction?.invoke(binding.priceEt.text.toString(), "$")
            dismiss()
        }
        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, currencies)
        binding.currencySpinner.adapter = adapter

        binding.currencySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Toast.makeText(requireContext(), currencies[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}