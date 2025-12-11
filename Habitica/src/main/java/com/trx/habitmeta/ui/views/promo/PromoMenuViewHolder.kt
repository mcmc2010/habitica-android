package com.trx.habitmeta.ui.views.promo

import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.models.promotions.HabiticaPromotion

class PromoMenuViewHolder(val promoView: PromoMenuView) : RecyclerView.ViewHolder(promoView) {
    fun bind(promo: HabiticaPromotion) {
        promo.configurePromoMenuView(promoView)
    }
}
