package com.trx.habitmeta.ui.views.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.trx.habitmeta.databinding.DialogAchievementDetailBinding
import com.trx.habitmeta.extensions.addCloseButton
import com.trx.habitmeta.models.Achievement
import com.trx.habitmeta.common.extensions.fromHtml
import com.trx.habitmeta.common.extensions.loadImage
import com.trx.habitmeta.common.views.PixelArtView

class AchievementDetailDialog(val achievement: Achievement, context: Context) :
    HabiticaAlertDialog(context) {
    private var iconView: PixelArtView?
    private var descriptionView: TextView?

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = DialogAchievementDetailBinding.inflate(inflater)
        binding.onboardingDoneIcon.visibility = View.GONE
        iconView = binding.iconView
        descriptionView = binding.descriptionView
        setAdditionalContentView(binding.root)
        setTitle(achievement.title)
        descriptionView?.setText(achievement.text?.fromHtml(), TextView.BufferType.SPANNABLE)
        val iconName =
            if (achievement.earned) {
                achievement.icon + "2x"
            } else {
                "achievement-unearned2x"
            }
        iconView?.loadImage(iconName)
        addCloseButton(true)
    }
}
