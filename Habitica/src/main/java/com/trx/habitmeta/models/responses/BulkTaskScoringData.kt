package com.trx.habitmeta.models.responses

import com.google.gson.annotations.SerializedName
import com.trx.habitmeta.models.user.Buffs
import com.trx.habitmeta.models.user.Training
import com.trx.habitmeta.shared.models.responses.TaskDirectionData

class BulkTaskScoringData {
    @SerializedName("con")
    var constitution: Int? = null

    @SerializedName("str")
    var strength: Int? = null

    @SerializedName("per")
    var per: Int? = null

    @SerializedName("int")
    var intelligence: Int? = null
    var training: Training? = null
    var buffs: Buffs? = null
    var points: Int? = null
    var lvl: Int? = null

    @SerializedName("class")
    var habitClass: String? = null
    var gp: Double? = null
    var exp: Double? = null
    var mp: Double? = null
    var hp: Double? = null

    var tasks: List<TaskDirectionData> = listOf()
}
