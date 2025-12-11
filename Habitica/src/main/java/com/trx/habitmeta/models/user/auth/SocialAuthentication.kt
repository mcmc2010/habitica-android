package com.trx.habitmeta.models.user.auth

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class SocialAuthentication : RealmObject(), BaseObject {
    var emails: RealmList<String> = RealmList()
}
