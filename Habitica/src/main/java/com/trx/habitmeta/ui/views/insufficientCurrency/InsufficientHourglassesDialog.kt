package com.trx.habitmeta.ui.views.insufficientCurrency

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.trx.habitmeta.R
import com.trx.habitmeta.extensions.addCloseButton
import com.trx.habitmeta.ui.views.HabiticaIconsHelper
import com.trx.habitmeta.common.helpers.MainNavigationController

class InsufficientHourglassesDialog(context: Context) : InsufficientCurrencyDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView.setImageBitmap(HabiticaIconsHelper.imageOfHourglassShop())
        textView.setText(R.string.insufficientHourglasses)

        addButton(
            R.string.get_hourglasses,
            true
        ) { _, _ ->
            MainNavigationController.navigate(
                R.id.gemPurchaseActivity,
                bundleOf(Pair("openSubscription", true))
            )
        }
        addCloseButton()
    }
}
