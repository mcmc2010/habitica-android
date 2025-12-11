package com.trx.habitmeta.data

import com.trx.habitmeta.models.inventory.Customization
import kotlinx.coroutines.flow.Flow

interface CustomizationRepository : BaseRepository {
    fun getCustomizations(
        type: String,
        category: String?,
        onlyAvailable: Boolean
    ): Flow<List<Customization>>
}
