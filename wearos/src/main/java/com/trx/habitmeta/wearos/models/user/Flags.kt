package com.trx.habitmeta.wearos.models.user

import com.trx.habitmeta.shared.models.AvatarFlags
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Flags : AvatarFlags {
    override var classSelected: Boolean = false
}
