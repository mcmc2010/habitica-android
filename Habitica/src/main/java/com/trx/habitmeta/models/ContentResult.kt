package com.trx.habitmeta.models

import com.trx.habitmeta.models.inventory.Customization
import com.trx.habitmeta.models.inventory.Egg
import com.trx.habitmeta.models.inventory.Equipment
import com.trx.habitmeta.models.inventory.EquipmentSet
import com.trx.habitmeta.models.inventory.Food
import com.trx.habitmeta.models.inventory.HatchingPotion
import com.trx.habitmeta.models.inventory.Mount
import com.trx.habitmeta.models.inventory.Pet
import com.trx.habitmeta.models.inventory.QuestContent
import com.trx.habitmeta.models.inventory.SpecialItem
import io.realm.RealmList

/**
 * Created by Negue on 15.07.2015.
 */
class ContentResult {
    var potion: Equipment? = null
    var armoire: Equipment? = null
    var gear: ContentGear? = null
    var quests = RealmList<QuestContent>()
    var eggs = RealmList<Egg>()
    var food = RealmList<Food>()
    var hatchingPotions = RealmList<HatchingPotion>()
    var pets = RealmList<Pet>()
    var mounts = RealmList<Mount>()
    var spells = RealmList<Skill>()
    var appearances = RealmList<Customization>()
    var backgrounds = RealmList<Customization>()
    var faq = RealmList<FAQArticle>()
    var special = RealmList<SpecialItem>()
    var mystery = RealmList<EquipmentSet>()
}
