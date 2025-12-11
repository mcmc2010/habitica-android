package com.trx.habitmeta.ui.views.insufficientCurrency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.trx.habitmeta.R
import com.trx.habitmeta.ui.views.dialogs.HabiticaAlertDialog

/**
 * Created by phillip on 27.09.17.
 */

abstract class InsufficientCurrencyDialog(context: Context) : HabiticaAlertDialog(context) {
    protected lateinit var imageView: ImageView
    protected lateinit var textView: TextView

    open fun getLayoutID(): Int {
        return R.layout.dialog_insufficient_currency
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(getLayoutID(), null)
        setAdditionalContentView(view)
        titleTextViewVisibility = false

        imageView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)
    }
}
