package com.tip.kuruma.controllers

import com.tip.kuruma.dto.SuscriptionDTO
import com.tip.kuruma.services.SuscriptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/suscriptions")
@CrossOrigin
class SuscriptionController @Autowired constructor(
    private val suscriptionService: SuscriptionService
) {
    @GetMapping
    fun getAllSuscriptions(): ResponseEntity<List<SuscriptionDTO>> {
        val suscriptions = suscriptionService.getAllSuscriptions()

        return ResponseEntity.ok(SuscriptionDTO.fromSuscriptions(suscriptions))
    }

    @GetMapping("/car/{carId}")
    fun getAllSuscriptionsByUserId(@PathVariable carId: Long): ResponseEntity<List<SuscriptionDTO>> {
        val suscriptions = suscriptionService.getAllSuscriptionsByUserId(carId)

        return ResponseEntity.ok(SuscriptionDTO.fromSuscriptions(suscriptions))
    }

    @PostMapping
    fun createSuscription(@RequestBody suscriptionDTO: SuscriptionDTO): ResponseEntity<SuscriptionDTO> {
        val savedSuscription = suscriptionService.saveSuscription(suscriptionDTO.toSuscription())
        return ResponseEntity.status(201).body(SuscriptionDTO.fromSuscription(savedSuscription))
    }

    @GetMapping("/{id}")
    fun getSuscriptionById(@PathVariable id: Long): ResponseEntity<SuscriptionDTO> {
        val suscription = suscriptionService.getSuscriptionById(id)
        return ResponseEntity.ok(SuscriptionDTO.fromSuscription(suscription))
    }

    @PutMapping("/{id}")
    fun updateSuscription(@PathVariable id: Long, @RequestBody suscriptionDTO: SuscriptionDTO): ResponseEntity<SuscriptionDTO> {
        val suscription = suscriptionService.updateSuscription(id, suscriptionDTO.toSuscription())
        return ResponseEntity.ok(SuscriptionDTO.fromSuscription(suscription))
    }

    @PatchMapping("/{id}")
    fun patchSuscription(@PathVariable id: Long, @RequestBody partialSuscriptionDTO: SuscriptionDTO): ResponseEntity<SuscriptionDTO> {
        val suscription = suscriptionService.patchSuscription(id, partialSuscriptionDTO.toSuscription())
        return ResponseEntity.ok(SuscriptionDTO.fromSuscription(suscription))
    }

    @DeleteMapping("/{id}")
    fun deleteSuscription(@PathVariable id: Long): ResponseEntity<Unit> {
        suscriptionService.deleteSuscription(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteSubscriptionByEndpoint(@RequestBody subscriptionDTO: SuscriptionDTO): ResponseEntity<Unit> {
        suscriptionService.deleteSubscriptionByUserAndEndpoint(subscriptionDTO.user_id!!, subscriptionDTO.endpoint!!)
        return ResponseEntity.noContent().build()
    }
}