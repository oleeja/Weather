package com.currentweather.ui.main.units

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.currentweather.databinding.FragmentUnitsBinding
import com.currentweather.ui.base.BaseFragment
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnitsFragment : BaseFragment() {

    private val unitsViewModel: UnitsViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUnitsBinding.inflate(inflater, container, false).also {
        lifecycle.addObserver(unitsViewModel)
        it.model = unitsViewModel
    }.root
}
