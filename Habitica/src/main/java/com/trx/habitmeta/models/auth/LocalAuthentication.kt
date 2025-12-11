package com.trx.habitmeta.models.auth

import com.google.gson.annotations.SerializedName
import com.trx.habitmeta.models.BaseObject
import com.trx.habitmeta.shared.models.AvatarLocalAuthentication
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LocalAuthentication : RealmObject(), BaseObject, AvatarLocalAuthentication {
    override var username: String? = null
    var email: String? = null

    @SerializedName("has_password")
    var hasPassword: Boolean? = false
}
