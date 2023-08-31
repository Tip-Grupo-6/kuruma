package com.tip.kuruma.repositories

import com.tip.kuruma.models.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long>
