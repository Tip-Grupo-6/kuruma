package com.tip.kuruma.controllers.car_data.v2

import com.tip.kuruma.dto.car_data.CarDetailDTO
import com.tip.kuruma.dto.car_data.CarMakeDTO
import com.tip.kuruma.dto.car_data.CarModelDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarMakeSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelDetailSanCristobalDTO
import com.tip.kuruma.dto.clients.san_cristobal.CarModelSanCristobalDTO
import com.tip.kuruma.services.CarDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v2/car_data")
class CarDataV2Controller(
        @Autowired
        @Qualifier("carDataServiceSanCristobal")
        private val carDataService: CarDataService<CarMakeSanCristobalDTO, CarModelSanCristobalDTO>
) {

    @GetMapping("/makes")
    fun getCarMakes(@RequestParam("year") year: Int): List<CarMakeDTO>? {
        return carDataService.getCarMakes(year)
    }

    @GetMapping("/models")
    fun getCarModels(@RequestParam("year") year: Int, @RequestParam("make_id") makeId: Int): List<CarModelDTO>? {
        return carDataService.getCarModel(year, makeId)
    }

    @GetMapping("/model_details")
    fun getCarModelDetails(@RequestParam("year") year: Int, @RequestParam("make_id") makeId: Int, @RequestParam("model_id") modelId: Int): List<CarDetailDTO>? {
        return carDataService.getCarModelDetails(year, makeId, modelId)
    }
}
