package com.trx.habitmeta.interactors

import android.app.Activity
import com.trx.habitmeta.helpers.PurchaseHandler
import com.trx.habitmeta.helpers.PurchaseTypes
import com.trx.habitmeta.ui.activities.MainActivity
import javax.inject.Inject

class InsufficientGemsUseCase
@Inject
constructor(
    private val purchaseHandler: PurchaseHandler
) : UseCase<InsufficientGemsUseCase.RequestValues, Unit>() {
    override suspend fun run(requestValues: RequestValues) {
        val activity = requestValues.activity as? MainActivity ?: return
        val gemSku =
            if (requestValues.gemPrice > 4) {
                PurchaseTypes.PURCHASE_21_GEMS
            } else {
                PurchaseTypes.PURCHASE_4_GEMS
            }
        val sku = purchaseHandler.getInAppPurchaseSKU(gemSku) ?: return
        purchaseHandler.purchase(activity, sku)
    }

    class RequestValues(val gemPrice: Int, val activity: Activity) : UseCase.RequestValues
}
