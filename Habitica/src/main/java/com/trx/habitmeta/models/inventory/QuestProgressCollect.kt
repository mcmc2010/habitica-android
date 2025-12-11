package com.trx.habitmeta.models.inventory

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class QuestProgressCollect : RealmObject(), BaseObject {
    var key: String? = null
    var count = 0
}
