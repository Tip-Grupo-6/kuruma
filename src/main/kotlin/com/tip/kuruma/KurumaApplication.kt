package com.tip.kuruma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.domain.EntityScan

@SpringBootApplication
@EntityScan(basePackages = ["com.tip.kuruma"]) // Replace with your entity package
class KurumaApplication

fun main(args: Array<String>) {
	runApplication<KurumaApplication>(*args)
}
