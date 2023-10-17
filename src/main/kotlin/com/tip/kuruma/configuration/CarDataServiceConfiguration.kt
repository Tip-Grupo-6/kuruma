package com.tip.kuruma.configuration

import com.tip.kuruma.client.CarApiClient
import com.tip.kuruma.client.SanCristobalClient
import com.tip.kuruma.mapper.CarApiMapper
import com.tip.kuruma.mapper.SanCristobalMapper
import com.tip.kuruma.services.CarDataService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CarDataServiceConfiguration {

    @Bean("carDataServiceSanCristobal")
    fun carDataServiceSanCristobal() = CarDataService(
            SanCristobalClient(),
            SanCristobalMapper()
    )

    @Bean("carDataServiceCarApi")
    fun carDataServiceCarApi() = CarDataService(
            CarApiClient(),
            CarApiMapper()
    )
}