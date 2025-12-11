package com.trx.habitmeta.ui.adapter.social

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.R
import com.trx.habitmeta.databinding.ProfileAchievementItemBinding
import com.trx.habitmeta.models.Achievement
import com.trx.habitmeta.ui.activities.MainActivity
import com.trx.habitmeta.ui.viewHolders.SectionViewHolder
import com.trx.habitmeta.ui.views.dialogs.AchievementDetailDialog
import com.trx.habitmeta.common.extensions.inflate
import com.trx.habitmeta.common.extensions.loadImage

class AchievementProfileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemType: String? = null
    var activity: MainActivity? = null
    private var itemList: List<Any> = emptyList()

    fun setItemList(itemList: List<Any>) {
        this.itemList = itemList
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            SectionViewHolder(parent.inflate(R.layout.profile_achievement_category))
        } else {
            AchievementViewHolder(parent.inflate(R.layout.profile_achievement_item))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val obj = this.itemList[position]
        if (obj.javaClass == String::class.java) {
            (holder as? SectionViewHolder)?.bind(obj as String)
        } else {
            (holder as? AchievementViewHolder)?.bind(itemList[position] as Achievement)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList.size <= position) return 0
        return if (this.itemList[position].javaClass == String::class.java) {
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    internal class AchievementViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val binding = ProfileAchievementItemBinding.bind(itemView)
        private var achievement: Achievement? = null

        init {
            itemView.isClickable = true
            itemView.setOnClickListener(this)
        }

        fun bind(item: Achievement) {
            binding.achievementDrawee.loadImage((if (!item.earned) "achievement-unearned" else item.icon) + "2x")

            this.achievement = item
            binding.achievementText.text = item.title

            if (item.optionalCount == null || item.optionalCount == 0) {
                binding.achievementCountLabel.visibility = View.GONE
            } else {
                binding.achievementCountLabel.visibility = View.VISIBLE
                binding.achievementCountLabel.text = item.optionalCount.toString()
            }
        }

        override fun onClick(view: View) {
            achievement?.let {
                AchievementDetailDialog(it, itemView.context).show()
            }
        }
    }
}
