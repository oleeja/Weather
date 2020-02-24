package com.currentweather.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.currentweather.R
import com.currentweather.databinding.FragmentSettingsBinding
import com.currentweather.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment() {

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).also {
        it.model = settingsViewModel
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"
        settingsViewModel.getViewModelLiveData().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                SettingsViewModel.Settings.UNITS -> findNavController().navigate(R.id.action_settingsFragment_to_unitsFragment)
                SettingsViewModel.Settings.NONE -> {}
                else -> {}
            }
        })
    }
}
