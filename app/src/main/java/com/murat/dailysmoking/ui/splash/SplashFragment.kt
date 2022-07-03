package com.murat.dailysmoking.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

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

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    SplashViewModel.Event.NavigateToOnBoardingScreen -> {
                        findNavController().navigate(SplashFragmentDirections.actionNavigateOnBoarding())
                    }
                    SplashViewModel.Event.NavigateToHome -> {
                        findNavController().navigate(SplashFragmentDirections.actionNavigateHome())
                    }
                }
            }
        }
    }

}