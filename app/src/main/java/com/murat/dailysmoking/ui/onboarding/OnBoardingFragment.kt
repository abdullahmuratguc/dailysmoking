package com.murat.dailysmoking.ui.onboarding

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murat.core.withError
import com.murat.core.withEvent
import com.murat.core.withProgress
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
        collectState()
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

    private fun collectState() = with(viewModel) {
        withProgress(this, ::onProgress)
        withError(this, ::onError)
        withEvent(this) { event ->
            when (event) {
                is OnBoardingViewModel.OnBoardingEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is OnBoardingViewModel.OnBoardingEvent.NavigateToHome -> {
                    findNavController().navigate(OnBoardingFragmentDirections.actionNavigateHome())
                }
            }
        }
    }
}