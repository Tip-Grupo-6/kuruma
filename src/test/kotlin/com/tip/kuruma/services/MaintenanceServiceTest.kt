package com.tip.kuruma.services

import com.tip.kuruma.*
import com.tip.kuruma.models.MaintenanceItem
import com.tip.kuruma.repositories.MaintenanceItemRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class MaintenanceServiceTest {

    private val repository: MaintenanceItemRepository = mockk()
    private val maintenanceService = MaintenanceService(repository)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `get all should no throw exception`() {
        GIVEN {
            every { repository.findAll() } returns listOf()
        }

        EXPECT_NOT_THROW {
            maintenanceService.getAll()
        }
        AND {
            verify(exactly = 1) {
                repository.findAll()
            }
        }
    }

    @Test
    fun `get by code should no throw exception`() {
        val code = "OIL"
        GIVEN {
            every { repository.findByCode(code) } returns Optional.of(MaintenanceItem())
        }
        EXPECT_NOT_THROW {
            maintenanceService.findByCode(code)
        }
        AND {
            verify(exactly = 1) {
                repository.findByCode(code)
            }
        }
    }

    @Test
    fun `get by non exists code should throw EntityNotFound`() {
        val code = "UNKNOWN_CODE"
        GIVEN {
            every { repository.findByCode(code) } returns Optional.empty()
        }
        EXPECT_TO_THROW<EntityNotFoundException> {
            maintenanceService.findByCode(code)
        }
        AND {
            verify(exactly = 1) {
                repository.findByCode(code)
            }
        }
    }

}
