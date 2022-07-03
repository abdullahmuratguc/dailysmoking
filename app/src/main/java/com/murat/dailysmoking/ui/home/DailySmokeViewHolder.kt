package com.murat.dailysmoking.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.murat.dailysmoking.data.ui.SmokeUiModel
import com.murat.dailysmoking.databinding.ItemDailySmokeListBinding

/**
 * Created by Murat
 */

class DailySmokeViewHolder(
    view: View,
    private val onClickSmoke: ((smoke: SmokeUiModel) -> Unit)?,
    private val onClickDelete: ((smoke: SmokeUiModel) -> Unit)?
) :
    RecyclerView.ViewHolder(view) {
    private val binding = ItemDailySmokeListBinding.bind(view)

    fun bind(uiModel: SmokeUiModel) = with(binding) {
        smokingTimeTv.text = uiModel.date
        smokePriceTv.text = uiModel.price

        itemView.setOnClickListener {
            onClickSmoke?.invoke(uiModel)
        }

        deleteIv.setOnClickListener {
            onClickDelete?.invoke(uiModel)
        }
    }
}