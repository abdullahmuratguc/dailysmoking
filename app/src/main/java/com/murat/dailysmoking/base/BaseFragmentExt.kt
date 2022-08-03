package com.murat.dailysmoking.base

import android.view.View
import androidx.viewbinding.ViewBinding
import murat.utils.viewbinding.FragmentViewBindingDelegate

/**
 * Created by Murat
 */

fun <T : ViewBinding> BaseFragment.contentViewBinding(
    viewBindingFactory: (View) -> T
) = FragmentViewBindingDelegate(this, viewBindingFactory) { contentView!! }