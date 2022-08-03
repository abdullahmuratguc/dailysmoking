package com.murat.dailysmoking.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Murat
 */

abstract class BaseViewHolder<T : ViewBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)