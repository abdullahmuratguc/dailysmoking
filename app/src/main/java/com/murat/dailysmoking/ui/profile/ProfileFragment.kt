package com.murat.dailysmoking.ui.profile

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.murat.core.withError
import com.murat.core.withEvent
import com.murat.core.withProgress
import com.murat.core.withUiState
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private val binding by contentViewBinding(FragmentProfileBinding::bind)

    override val layoutId = R.layout.fragment_profile

    private val viewModel: ProfileViewModel by viewModels()

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

        binding.updateBtn.setOnClickListener {
            viewModel.updateClick(
                packagePrice = binding.priceEt.text.toString(),
                currency = binding.currencyTv.text.toString(),
                packageContent = binding.cigaretteCountTv.text.toString()
            )
        }
    }

    private fun collectState() = with(viewModel) {
        withUiState(this, ::setData)
        withProgress(this, ::onProgress)
        withError(this, ::onError)
        withEvent(this) { event ->
            when (event) {
                is ProfileViewModel.ProfileEvent.UpdateUser -> {
                    Toast.makeText(
                        context,
                        getString(R.string.profile_user_update_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ProfileViewModel.ProfileEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setData(state: ProfileViewModel.ProfileUiState) {
        binding.currencyTv.setText(state.user?.currency.orEmpty(), false)
        binding.priceEt.setText(state.user?.packagePrice?.toString().orEmpty())
        binding.cigaretteCountTv.setText(
            state.user?.packageContent?.toString().orEmpty(),
            false
        )
    }
}