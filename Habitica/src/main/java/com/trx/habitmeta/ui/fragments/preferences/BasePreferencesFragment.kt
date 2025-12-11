package com.trx.habitmeta.ui.fragments.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView
import com.trx.habitmeta.R
import com.trx.habitmeta.data.InventoryRepository
import com.trx.habitmeta.data.UserRepository
import com.trx.habitmeta.extensions.applyScrollContentWindowInsets
import com.trx.habitmeta.models.user.User
import com.trx.habitmeta.ui.viewmodels.MainUserViewModel
import javax.inject.Inject

abstract class BasePreferencesFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userViewModel: MainUserViewModel

    @Inject
    lateinit var inventoryRepository: InventoryRepository

    internal open var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel.user.observe(viewLifecycleOwner) { setUser(it) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler_view)?.let {
            applyScrollContentWindowInsets(it)
        }
    }

    override fun onDestroy() {
        userRepository.close()
        super.onDestroy()
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences_fragment, rootKey)
        setupPreferences()
    }

    protected abstract fun setupPreferences()

    open fun setUser(user: User?) {
        this.user = user
    }
}
