package com.trx.habitmeta.models.social

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class ChallengeMembership : RealmObject, BaseObject {
    var userID: String = ""
    var challengeID: String = ""

    constructor(userID: String, challengeID: String) : super() {
        this.userID = userID
        this.challengeID = challengeID
    }

    constructor() : super()
}
