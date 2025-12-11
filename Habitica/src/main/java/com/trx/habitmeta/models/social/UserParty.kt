package com.trx.habitmeta.models.social

import com.google.gson.annotations.SerializedName
import com.trx.habitmeta.models.BaseObject
import com.trx.habitmeta.models.inventory.Quest
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.Date

@RealmClass(embedded = true)
open class UserParty : RealmObject(), BaseObject {
    @SerializedName("_id")
    var id: String = ""
    var quest: Quest? = null
    var seeking: Date? = null

    @SerializedName("order")
    var partyOrder: String? = null // Order to display ppl
    var orderAscending: String? = null // Order type
}
