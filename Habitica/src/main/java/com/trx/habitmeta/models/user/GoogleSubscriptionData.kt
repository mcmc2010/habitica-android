package com.trx.habitmeta.models.user

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class GoogleSubscriptionData : RealmObject() {
    var orderId: String? = null
    var productId: String? = null
}
