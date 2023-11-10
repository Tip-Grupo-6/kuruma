package com.tip.kuruma.configuration.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.tip.kuruma.dto.ErrorDTO
import com.tip.kuruma.services.auth.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
        val jwtService: JwtService,
        val userDetailsService: UserDetailsService
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//        val token = getTokenFromRequest(request) ?: run {
//            val error = ErrorDTO("UNAUTHORIZED", "User is not logged")
//            response.status = HttpStatus.UNAUTHORIZED.value()
//            response.addHeader("Content-Type", "application/json")
//            return response.writer.write(ObjectMapper().writeValueAsString(error))
//        }
        val token = getTokenFromRequest(request) ?: return filterChain.doFilter(request, response)

        val username = jwtService.getUsernameFromToken(token)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
            if (jwtService.isTokenValid(token, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }
        return null
    }
}