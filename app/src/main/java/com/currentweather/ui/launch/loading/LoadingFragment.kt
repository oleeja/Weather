package com.currentweather.ui.launch.loading

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.currentweather.R
import com.currentweather.databinding.FragmentLoadingBinding
import com.currentweather.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadingFragment : BaseFragment() {

    private val loadingViewModel: LoadingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoadingBinding.inflate(inflater, container, false).also {
        subscribeUI()
    }.root

    private fun subscribeUI() {
        loadingViewModel.loading.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is LoadingViewModel.LoadingState.LOADED -> {
                    findNavController().navigate(R.id.action_loadingFragment_to_mainActivity)
                }
                is LoadingViewModel.LoadingState.NOT_LOADED -> Toast.makeText(
                    context,
                    result.throwable.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        GlobalScope.launch(Dispatchers.Main) {
            val permissionResult =
                permissions.requestPermissions(listOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            if (permissionResult.isAllGranted) {
                loadingViewModel.loadLocation()
            } else {
                startActivity(Intent (Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://pick.location/google")
                })
            }
        }
    }

}
