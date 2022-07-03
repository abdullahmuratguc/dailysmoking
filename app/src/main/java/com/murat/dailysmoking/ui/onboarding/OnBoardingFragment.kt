package com.murat.dailysmoking.ui.onboarding

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Murat
 */

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment() {

    private val binding by contentViewBinding(FragmentOnboardingBinding::bind)

    override val layoutId = R.layout.fragment_onboarding

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun initViews() {
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

        binding.continueBtn.setOnClickListener {
            viewModel.continueClick(
                packagePrice = binding.priceEt.text.toString(),
                currency = binding.currencyTv.text.toString(),
                packageContent = binding.cigaretteCountTv.text.toString()
            )
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is OnBoardingViewModel.Event.Error -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }

                    is OnBoardingViewModel.Event.NavigateToHome -> {
                        findNavController().navigate(OnBoardingFragmentDirections.actionNavigateHome())
                    }
                }
            }
        }
    }
}