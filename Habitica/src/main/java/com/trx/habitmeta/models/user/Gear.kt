package com.trx.habitmeta.models.user

import com.trx.habitmeta.models.BaseObject
import com.trx.habitmeta.models.inventory.Equipment
import com.trx.habitmeta.shared.models.AvatarGear
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class Gear : RealmObject(), BaseObject, AvatarGear {
    var owned: RealmList<Equipment>? = null
    override var equipped: Outfit? = null
    override var costume: Outfit? = null
}
