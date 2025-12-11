package com.trx.habitmeta.data.local

import com.trx.habitmeta.models.inventory.Customization
import kotlinx.coroutines.flow.Flow

interface CustomizationLocalRepository : ContentLocalRepository {
    fun getCustomizations(
        type: String,
        category: String?,
        onlyAvailable: Boolean
    ): Flow<List<Customization>>
}
