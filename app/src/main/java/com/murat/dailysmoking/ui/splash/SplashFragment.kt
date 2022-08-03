package com.murat.dailysmoking.ui.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murat.core.withError
import com.murat.core.withEvent
import com.murat.core.withProgress
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import com.murat.dailysmoking.ui.splash.SplashViewModel.SplashEvent.*

/**
 * Created by Murat
 */

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val binding by contentViewBinding(FragmentSplashBinding::bind)

    private val viewModel: SplashViewModel by viewModels()

    override val layoutId = R.layout.fragment_splash

    override fun initViews() {
        observe()
        viewModel.startSplash()
    }

    private fun observe() = with(viewModel) {
        withProgress(this, ::onProgress)
        withError(this, ::onError)
        withEvent(this) { event ->
            when (event) {
                NavigateToOnBoardingScreen -> {
                    findNavController().navigate(SplashFragmentDirections.actionNavigateOnBoarding())
                }
                NavigateToHome -> {
                    findNavController().navigate(SplashFragmentDirections.actionNavigateHome())
                }
            }
        }
    }
}