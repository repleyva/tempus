package com.repleyva.tempus.domain.use_cases.weather

import com.repleyva.tempus.domain.common.DataState
import com.repleyva.tempus.domain.error_mapper.GenericErrorMapper
import com.repleyva.tempus.domain.repository.WeatherRepository
import com.repleyva.tempus.domain.use_cases.base.BaseUseCase
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : BaseUseCase() {

    operator fun invoke(city: String) = handlerErrorMapper(GenericErrorMapper) {
        send(DataState.Loading())
        val response = weatherRepository.getWeather(city)
        send(DataState.Data(response))
    }

}

