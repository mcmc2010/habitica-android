package com.trx.habitmeta.ui.fragments.support

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.trx.habitmeta.R
import com.trx.habitmeta.data.FAQRepository
import com.trx.habitmeta.databinding.FragmentSupportMainBinding
import com.trx.habitmeta.helpers.AppConfigManager
import com.trx.habitmeta.ui.fragments.BaseMainFragment
import com.trx.habitmeta.ui.views.HabiticaSnackbar
import com.trx.habitmeta.common.helpers.ExceptionHandler
import com.trx.habitmeta.common.helpers.MainNavigationController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SupportMainFragment : BaseMainFragment<FragmentSupportMainBinding>() {
    override var binding: FragmentSupportMainBinding? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSupportMainBinding {
        return FragmentSupportMainBinding.inflate(inflater, container, false)
    }

    @Inject
    lateinit var faqRepository: FAQRepository

    @Inject
    lateinit var appConfigManager: AppConfigManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hidesToolbar = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.usingHabiticaWrapper?.setOnClickListener {
            MainNavigationController.navigate(R.id.FAQOverviewFragment)
        }
        binding?.bugsFixesWrapper?.setOnClickListener {
            MainNavigationController.navigate(R.id.bugFixFragment)
        }
        binding?.suggestionsFeedbackWrapper?.setOnClickListener {
            if (appConfigManager.feedbackURL().isNotBlank()) {
                val uriUrl = appConfigManager.feedbackURL().toUri()
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }
        }

        binding?.resetTutorialButton?.setOnClickListener {
            lifecycleScope.launch(ExceptionHandler.coroutine()) {
                userRepository.resetTutorial()
                mainActivity?.showSnackbar(
                    null,
                    null,
                    getString(R.string.tutorial_reset_confirmation),
                    displayType = HabiticaSnackbar.SnackbarDisplayType.SUCCESS
                )
            }
        }
    }

    override fun onDestroy() {
        faqRepository.close()
        super.onDestroy()
    }
}
