package com.tip.kuruma.services.auth

import com.tip.kuruma.dto.auth.UserToken
import com.tip.kuruma.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@Service
class JwtService {

    companion object {
        const val SECRET_KEY = "s3cr3tK3yKurum4F0rT0k3nCr34t10nW1thL0ngL0ngK3y"
        private val LOGGER = LoggerFactory.getLogger(JwtService::class.java)
    }

    fun getToken(user: User): String {
        LOGGER.info("Creating token for user ${user.username}")
        return getTokenWithExtraClaims(HashMap(), user)
    }

    private fun getTokenWithExtraClaims(extraClaims: HashMap<String, Any>, user: User): String {
        val userToken = UserToken(user.id, user.email, user.name, "1")
        return Jwts.builder()
                .claims()
                .empty()
                .add(extraClaims)
                .and()
                .subject(user.username)
                .claim("user", userToken)
                .issuedAt(Date())
                .expiration(LocalDateTime.now().plusDays(1).toDate())
                .signWith(getKey())
                .compact()
    }

    private fun getKey(): Key {
        val keyBytes: ByteArray = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun getUsernameFromToken(token: String): String? {
        return getClaims(token).subject
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseSignedClaims(token)
                .payload
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return getExpiration(token).before(Date())
    }

    private fun getExpiration(token: String): Date {
        return getClaims(token).expiration
    }

}

private fun LocalDateTime.toDate(): Date {
    return Date.from(
            this.atZone(ZoneId.systemDefault()).toInstant()
    )
}

