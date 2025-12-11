package com.trx.habitmeta.models.user

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class ABTest : RealmObject(), BaseObject {
    var name: String = ""
    var group: String = ""
}
