package com.murat.dailysmoking.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.murat.dailysmoking.R
import com.murat.dailysmoking.databinding.FragmentBaseBinding
import murat.utils.viewbinding.viewBinding

/**
 * Created by Murat
 */

abstract class BaseFragment  : Fragment() {

    private val binding by viewBinding(FragmentBaseBinding::bind)

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun initViews()

    // Used to bind child views
    var contentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_base, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderContent()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contentView = null
    }

    private fun renderContent() {
        binding.contentViewStub.layoutResource = layoutId
        contentView = binding.contentViewStub.inflate()
    }

    protected fun onProgress(loading: Boolean) {
        if (loading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() = with(binding) {
        if (circularProgressIndicator.isVisible) return

        circularProgressIndicator.show()
    }

    private fun hideProgress() {
        binding.circularProgressIndicator.hide()
    }

    protected fun onError(exception: Exception, buttonClicked: () -> Unit = {}) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(exception.message)
            .setPositiveButton(
                getString(R.string.alert_dialog_positive_button)
            ) { _, _ -> buttonClicked() }
            .show()
    }
}