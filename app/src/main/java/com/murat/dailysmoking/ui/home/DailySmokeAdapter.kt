package com.murat.dailysmoking.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseListAdapter
import com.murat.dailysmoking.data.ui.SmokeUiModel

/**
 * Created by Murat
 */
class DailySmokeAdapter(
    private val onClickSmoke: ((smoke: SmokeUiModel) -> Unit)?,
    private val onClickDelete: ((smoke: SmokeUiModel) -> Unit)?
) :
    BaseListAdapter<SmokeUiModel>(
        itemsSame = { old, new -> old.id == new.id },
        contentsSame = { old, new -> old == new }
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_smoke_list, parent, false)
        return DailySmokeViewHolder(view, onClickSmoke, onClickDelete)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailySmokeViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }
}