package com.trx.habitmeta.wearos.models.user

import com.trx.habitmeta.shared.models.AvatarItems
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Items : AvatarItems {
    override var gear: Gear? = null

    override var currentMount: String? = null
    override var currentPet: String? = null
}
