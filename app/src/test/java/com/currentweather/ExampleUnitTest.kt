package com.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.currentweather.data.model.WeatherModel
import com.currentweather.ui.WeatherViewModel
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var weatherViewModel: WeatherViewModel

    @Mock
    private lateinit var viewStateObserver: Observer<WeatherViewModel.ViewState>

    private val dispatcherProvider = TestContextProvider()

    @Mock
    private lateinit var data: Response<WeatherModel>

    private val validCityId = 2172797

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel().apply {
            getViewModelLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `should success when fetchFromServer returns proper data`() =
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(weatherViewModel.repository.getWeaterData(validCityId)).thenReturn(data)

            // When
            weatherViewModel.getData(dispatcherProvider)

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Success(data))
        }

    @Test
    fun `should fail when fetchFromServer throws exception`() =
        testCoroutineRule.runBlockingTest {
            // Given
            val error = Throwable()
            whenever(weatherViewModel.repository.getWeaterData(validCityId)).thenThrow(error)

            // When
            weatherViewModel.getData(dispatcherProvider)

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Error(error))
        }
}
