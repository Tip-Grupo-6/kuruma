package com.tip.kuruma.controllers.car_data.v1

import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.car_api.CarApiListBrandDTO
import com.tip.kuruma.dto.clients.car_api.CarApiListModelDTO
import com.tip.kuruma.services.CarDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/car_data")
class CarDataV1Controller(
        @Autowired
        @Qualifier("carDataServiceCarApi")
        private val carDataService: CarDataService<CarApiListBrandDTO, CarApiListModelDTO>
) {

    @GetMapping("/makes")
    fun getCarMakes(@RequestParam("year") year: Int): List<CarMakeDTO>? {
        return carDataService.getCarMakes(year)
    }

    @GetMapping("/models")
    fun getCarModels(@RequestParam("year") year: Int, @RequestParam("make_id") makeId: Int): List<CarModelDTO>? {
        return carDataService.getCarModel(year, makeId)
    }
}
