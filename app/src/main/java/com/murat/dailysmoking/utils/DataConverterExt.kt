package com.murat.dailysmoking.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.murat.dailysmoking.data.CurrencyListModel
import murat.utils.extensions.tryCatch

/**
 * Created by Murat
 */

inline fun <reified T> Gson.fromJson(json: String?): T? {
    return tryCatch {
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    }
}

fun currencySymbols(): Array<String> {
    val currencyListModel = Gson().fromJson<List<CurrencyListModel>>(Currencies.currencyList)

    val symbols = currencyListModel?.filter {
        it.symbol != null
    }?.map {
        it.symbol!!
    }?.toMutableList()?: mutableListOf()

    symbols.add(0, "₺")

    symbols.distinct().forEach {
        Log.e("AMG", "currencySymbols: <item>$it</item>" )
    }

    return symbols.distinct().toTypedArray()
}