package com.murat.dailysmoking.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.murat.dailysmoking.R
import com.murat.dailysmoking.databinding.FragmentProfileBinding
import com.murat.dailysmoking.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observe()
    }

    private fun initUI() {
        val currencyList = resources.getStringArray(R.array.currencies)
        val packageContentList = resources.getStringArray(R.array.package_content)

        val currencyListAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown_list, currencyList)
        (binding.currencyTv as? AutoCompleteTextView)?.setAdapter(currencyListAdapter)

        val packageContentListAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown_list, packageContentList)
        (binding.cigaretteCountTv as? AutoCompleteTextView)?.setAdapter(packageContentListAdapter)

        binding.updateBtn.setOnClickListener {
            viewModel.updateClick(
                packagePrice = binding.priceEt.text.toString(),
                currency = binding.currencyTv.text.toString(),
                packageContent = binding.cigaretteCountTv.text.toString()
            )
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.eventsFlow.collect(::setUi)
        }
    }

    private fun setUi(event: ProfileViewModel.Event) {
        when (event) {
            is ProfileViewModel.Event.UserData -> {
                binding.currencyTv.setText(event.user?.currency.orEmpty(), false)
                binding.priceEt.setText(event.user?.packagePrice?.toString().orEmpty())
                binding.cigaretteCountTv.setText(
                    event.user?.packageContent?.toString().orEmpty(),
                    false
                )
            }

            is ProfileViewModel.Event.UpdateUser -> {
                Toast.makeText(
                    context,
                    getString(R.string.prrofile_user_update_success),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ProfileViewModel.Event.Error -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}