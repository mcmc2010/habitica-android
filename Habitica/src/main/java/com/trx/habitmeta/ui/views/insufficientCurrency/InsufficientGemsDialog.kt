package com.trx.habitmeta.ui.views.insufficientCurrency

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.trx.habitmeta.R
import com.trx.habitmeta.extensions.addCloseButton
import com.trx.habitmeta.helpers.Analytics
import com.trx.habitmeta.helpers.AnalyticsTarget
import com.trx.habitmeta.helpers.AppConfigManager
import com.trx.habitmeta.helpers.EventCategory
import com.trx.habitmeta.helpers.HitType
import com.trx.habitmeta.helpers.PurchaseHandler
import com.trx.habitmeta.helpers.PurchaseTypes
import com.trx.habitmeta.interactors.InsufficientGemsUseCase
import com.trx.habitmeta.common.helpers.MainNavigationController
import com.trx.habitmeta.common.helpers.launchCatching
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withContext

/**
 * Created by phillip on 27.09.17.
 */

class InsufficientGemsDialog(val parentActivity: Activity, var gemPrice: Int) :
    InsufficientCurrencyDialog(parentActivity) {
    var configManager: AppConfigManager
    var purchaseHandler: PurchaseHandler

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface InsufficientGemsDialogEntryPoint {
        fun configManager(): AppConfigManager

        fun purchaseHandler(): PurchaseHandler

        fun insufficientGemsUseCase(): InsufficientGemsUseCase
    }

    var insufficientGemsUseCase: InsufficientGemsUseCase

    init {
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(
                parentActivity,
                InsufficientGemsDialogEntryPoint::class.java
            )
        insufficientGemsUseCase = hiltEntryPoint.insufficientGemsUseCase()
        configManager = hiltEntryPoint.configManager()
        purchaseHandler = hiltEntryPoint.purchaseHandler()
    }

    override fun getLayoutID(): Int {
        return R.layout.dialog_insufficient_gems
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.setText(R.string.insufficientGems)
        addButton(
            R.string.see_other_options,
            true
        ) { _, _ ->
            MainNavigationController.navigate(
                R.id.gemPurchaseActivity,
                bundleOf(Pair("openSubscription", false))
            )
        }
        addCloseButton()
        contentView.setPadding(0, 0, 0, 0)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val purchaseTextView = contentView.findViewById<TextView>(R.id.purchase_textview)
        val purchaseButton = contentView.findViewById<Button>(R.id.purchase_button)
        purchaseHandler.startListening()
        val gemSku =
            if (gemPrice > 4) {
                purchaseTextView.text = "21"
                PurchaseTypes.PURCHASE_21_GEMS
            } else {
                purchaseTextView.text = "4"
                PurchaseTypes.PURCHASE_4_GEMS
            }
        CoroutineScope(Dispatchers.IO).launchCatching {
            val sku =
                purchaseHandler.getInAppPurchaseSKU(gemSku)
                    ?: return@launchCatching
            withContext(Dispatchers.Main) {
                purchaseButton?.text = sku.oneTimePurchaseOfferDetails?.formattedPrice
                contentView.findViewById<ProgressBar>(R.id.loading_indicator).isVisible = false
                purchaseButton.isVisible = true

                purchaseButton?.setOnClickListener {
                    Analytics.sendEvent(
                        "purchased_gems_from_insufficient",
                        EventCategory.BEHAVIOUR,
                        HitType.EVENT,
                        mapOf(Pair("gemPrice", gemPrice), Pair("sku", "")),
                        AnalyticsTarget.FIREBASE
                    )
                    MainScope().launchCatching {
                        insufficientGemsUseCase.callInteractor(
                            InsufficientGemsUseCase.RequestValues(
                                gemPrice,
                                parentActivity
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        purchaseHandler.stopListening()
        super.onDetachedFromWindow()
    }
}
