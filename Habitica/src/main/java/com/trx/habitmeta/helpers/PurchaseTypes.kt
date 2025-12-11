package com.trx.habitmeta.helpers

object PurchaseTypes {
    const val JUBILANT_GRYPHATRICE = "com.trx.habitmeta.iap.pets.gryphatrice_jubilant"
    const val PURCHASE_4_GEMS = "com.trx.habitmeta.iap.4gems"
    const val PURCHASE_21_GEMS = "com.trx.habitmeta.iap.21gems"
    const val PURCHASE_42_GEMS = "com.trx.habitmeta.iap.42gems"
    const val PURCHASE_84_GEMS = "com.trx.habitmeta.iap.84gems"
    val allGemTypes = listOf(PURCHASE_4_GEMS, PURCHASE_21_GEMS, PURCHASE_42_GEMS, PURCHASE_84_GEMS)
    const val SUBSCRIPTION_1_MONTH = "com.trx.habitmeta.subscription.1month"
    const val SUBSCRIPTION_3_MONTH = "com.trx.habitmeta.subscription.3month"
    const val SUBSCRIPTION_6_MONTH = "com.trx.habitmeta.subscription.6month"
    const val SUBSCRIPTION_12_MONTH = "com.trx.habitmeta.subscription.12month"
    val allSubscriptionTypes =
        mutableListOf(
            SUBSCRIPTION_1_MONTH,
            SUBSCRIPTION_3_MONTH,
            SUBSCRIPTION_6_MONTH,
            SUBSCRIPTION_12_MONTH
        )
    const val SUBSCRIPTION_1_MONTH_NORENEW =
        "com.trx.habitmeta.norenew_subscription.1month"
    const val SUBSCRIPTION_3_MONTH_NORENEW =
        "com.trx.habitmeta.norenew_subscription.3month"
    const val SUBSCRIPTION_6_MONTH_NORENEW =
        "com.trx.habitmeta.norenew_subscription.6month"
    const val SUBSCRIPTION_12_MONTH_NORENEW =
        "com.trx.habitmeta.norenew_subscription.12month"
    var allSubscriptionNoRenewTypes =
        listOf(
            SUBSCRIPTION_1_MONTH_NORENEW,
            SUBSCRIPTION_3_MONTH_NORENEW,
            SUBSCRIPTION_6_MONTH_NORENEW,
            SUBSCRIPTION_12_MONTH_NORENEW
        )
}
