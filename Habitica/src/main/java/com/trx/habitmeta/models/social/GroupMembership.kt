package com.trx.habitmeta.models.social

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GroupMembership : RealmObject, BaseObject {
    @PrimaryKey
    var combinedID: String = ""

    var userID: String = ""
        set(value) {
            field = value
            combinedID = userID + groupID
        }
    var groupID: String = ""
        set(value) {
            field = value
            combinedID = userID + groupID
        }

    constructor(userID: String, groupID: String) : super() {
        this.userID = userID
        this.groupID = groupID
    }

    constructor() : super()
}
