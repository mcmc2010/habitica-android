package com.trx.habitmeta.wearos.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Profile {
    var name: String? = null
}
