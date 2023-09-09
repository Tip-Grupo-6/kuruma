package com.tip.kuruma.dto

import java.time.LocalDate

data class MaintenanceStatusDTO(
        val nextOilChangeDue: LocalDate? = null,
        val nextWaterCheckDue: LocalDate? = null,
        val nextTirePressureCheckDue: LocalDate? = null,
        val oilChangeDue: Boolean? = null,
        val waterCheckDue: Boolean? = null,
        val tirePressureCheckDue: Boolean? = null,
        val carStatus: String? = null
)
