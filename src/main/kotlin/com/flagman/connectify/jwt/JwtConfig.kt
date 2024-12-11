package com.flagman.connectify.jwt

import io.jsonwebtoken.security.Keys
import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Getter
@Component
class JwtConfig(
    @Value("\${jwt.secret}")
    private val secret: String = "",
    @Value("\${jwt.access-token-expiration:60000}")
    private val accessTokenExpiration: Long = 60000,
    @Value("\${jwt.refresh-token-expiration:3600000}")
    private val refreshTokenExpiration: Long = 60000
) {
    private var jwtKey: SecretKey? = null

    fun secret() = secret
    fun getRefreshTokenExpiration() = refreshTokenExpiration
    fun getAccessTokenExpiration() = accessTokenExpiration

    fun getJwtKey(): SecretKey {
        if (jwtKey == null) {
            jwtKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))
        }
        return jwtKey!!
    }
}