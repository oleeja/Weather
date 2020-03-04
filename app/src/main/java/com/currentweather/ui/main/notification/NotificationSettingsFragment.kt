package com.currentweather.ui.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.currentweather.databinding.FragmentNotificationSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationSettingsFragment : Fragment() {

    private val notificationViewModel: NotificationSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNotificationSettingsBinding.inflate(inflater, container, false).also {
        lifecycle.addObserver(notificationViewModel)
        it.lifecycleOwner = this
        it.model = notificationViewModel
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                notificationViewModel.saveSettings()
            }
        })
    }.root
}
