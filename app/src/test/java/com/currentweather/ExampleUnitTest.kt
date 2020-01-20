package com.currentweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.currentweather.data.Repository
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
    private lateinit var repository: Repository

    @Mock
    private lateinit var data: WeatherModel

    private val validCityId = 2172797

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherViewModel = WeatherViewModel(repository,
            TestContextProvider()
        ).apply {
            getViewModelLiveData().observeForever(viewStateObserver)
        }
    }

    @Test
    fun `should success when getData() returns proper data`() =
        testCoroutineRule.runBlockingTest {
            // Given
            whenever(repository.getWeaterData(validCityId)).thenReturn(data)

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
            whenever(repository.getWeaterData(validCityId)).then { throw error }

            // When
            weatherViewModel.getData()

            // Then
            verify(viewStateObserver).onChanged(WeatherViewModel.ViewState.Error(error))
        }
}
