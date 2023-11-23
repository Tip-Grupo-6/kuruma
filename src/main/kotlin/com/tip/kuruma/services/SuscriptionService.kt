package com.tip.kuruma.services

import com.tip.kuruma.EntityNotFoundException
import com.tip.kuruma.models.Suscription
import com.tip.kuruma.repositories.SuscriptionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SuscriptionService @Autowired constructor(
    private val suscriptionRepository: SuscriptionRepository
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SuscriptionService::class.java)
    }


    fun getAllSuscriptions(): List<Suscription> = suscriptionRepository.findAllByIsDeletedIsFalse()

    fun getAllSuscriptionsByUserId(userId: Long): List<Suscription> = suscriptionRepository.getAllSuscriptionsByUserId(userId)

    fun saveSuscription(suscription: Suscription): Suscription = suscriptionRepository.save(suscription)

    fun getSuscriptionById(id: Long): Suscription = suscriptionRepository.findById(id).orElseThrow { EntityNotFoundException("Suscription with id $id not found") }

    fun updateSuscription(id: Long, suscription: Suscription): Suscription {
        val suscriptionToUpdate = getSuscriptionById(id)
        suscriptionToUpdate.let {
            it.auth = suscription.auth
            it.key = suscription.key
            it.endpoint = suscription.endpoint
            it.isDeleted = suscription.isDeleted
            it.updated_at = LocalDate.now()
            return saveSuscription(it)
        }
    }

    fun patchSuscription(id: Long, suscription: Suscription): Suscription {
        val suscriptionToUpdate = getSuscriptionById(id)
        suscriptionToUpdate.let {
            it.auth = suscription.auth ?: it.auth
            it.key = suscription.key ?: it.key
            it.endpoint = suscription.endpoint ?: it.endpoint
            it.isDeleted = suscription.isDeleted ?: it.isDeleted
            it.updated_at = LocalDate.now()
            return saveSuscription(it)
        }
    }

    fun deleteSuscription(id: Long) {
        val suscription = getSuscriptionById(id)
        suscription.isDeleted = true
        saveSuscription(suscription)
    }

    fun deleteSubscriptionByUserAndEndpoint(userId: Long, endpoint: String) {
        LOGGER.info("Finding subscription for user id $userId and endpoint $endpoint")
        suscriptionRepository.getByUserIdAndEndpoint(userId, endpoint)?.let {
            saveSuscription(it.copy(isDeleted = true))
            LOGGER.info("Subscription deleted")
        }
    }

}
