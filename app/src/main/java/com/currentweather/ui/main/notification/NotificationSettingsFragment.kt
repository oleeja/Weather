package com.currentweather.ui.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.currentweather.databinding.FragmentNotificationSettingsBinding
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationSettingsFragment : Fragment() {

    private val notificationViewModel: NotificationSettingsViewModel by currentScope.viewModel(this)

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
        it.navDirection = NotificationSettingsFragmentDirections.actionNotificationSettingsFragmentToLocationPickerFragment()
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationViewModel.newDestination.observe(viewLifecycleOwner, Observer { result ->
            if(result != null){
                findNavController().navigate(result)
            }
        } )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == android.R.id.home){
            notificationViewModel.saveSettings()
            false
        }
        else super.onOptionsItemSelected(item)
    }
}
