package com.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.currentweather.data.Repository
import com.currentweather.data.model.WeatherModel
import com.currentweather.ui.WeatherViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var weatherViewModel: WeatherViewModel
    @Mock
    private lateinit var repository: Repository
    @Mock
    private lateinit var viewStateObserver: Observer<WeatherViewModel.ViewState>

    @Mock
    private lateinit var data: Response<WeatherModel>

    private val validCityId = 2172797

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel().apply {
            getData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `should success when fetchFromServer returns proper data`() =
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(repository.getWeaterData(validCityId)).thenReturn(data)

            // When
            weatherViewModel.getData()

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Loading)
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Success(data))
        }

    @Test
    fun `should fail when fetchFromServer throws exception`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val error = Error()
            whenever(repository.getWeaterData(validCityId)).thenThrow(error)

            // When
            weatherViewModel.getData()

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Loading)
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Error(error))
        }
}
