package com.trx.habitmeta.wearos.models.user

import com.trx.habitmeta.shared.models.AvatarGear
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Gear : AvatarGear {
    override var equipped: Outfit? = null
    override var costume: Outfit? = null
}
