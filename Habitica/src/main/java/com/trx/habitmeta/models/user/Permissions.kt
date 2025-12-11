package com.trx.habitmeta.models.user

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject

open class Permissions : RealmObject(), BaseObject {
    var userSupport: Boolean = false
    var fullAccess: Boolean = false

    var moderator: Boolean = false
}
