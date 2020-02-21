package com.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.currentweather.data.CurrentWeatherRepositoryImpl
import com.currentweather.domain.model.WeatherModel
import com.currentweather.ui.main.current_weather.WeatherViewModel
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var weatherViewModel: WeatherViewModel

    @Mock
    private lateinit var viewStateObserver: Observer<WeatherViewModel.ViewState>

    @Mock
    private lateinit var currentWeatherRepositoryImpl: CurrentWeatherRepositoryImpl

    @Mock
    private lateinit var data: WeatherModel

    private val validCityId = 2172797

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel(
            currentWeatherRepositoryImpl,
            TestContextProvider()
        ).apply {
            getViewModelLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `should success when getData() returns proper data`() =
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(currentWeatherRepositoryImpl.getWeaterData(validCityId)).thenReturn(data)

            // When
            weatherViewModel.getData()

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Success(data))
        }

    @Test
    fun `should fail when fetchFromServer throws exception`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val error = Exception()
            whenever(currentWeatherRepositoryImpl.getWeaterData(validCityId)).then { throw error }

            // When
            weatherViewModel.getData()

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Error(error))
        }
}
