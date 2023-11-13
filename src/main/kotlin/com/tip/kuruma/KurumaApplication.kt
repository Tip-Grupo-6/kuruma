package com.tip.kuruma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
@EntityScan("com.tip.kuruma")
class KurumaApplication {

	@Bean
	fun corsConfigurer(): WebMvcConfigurer? {
		return object : WebMvcConfigurer {
			override fun addCorsMappings(registry: CorsRegistry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080", "http://localhost:3000")
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<KurumaApplication>(*args)
}