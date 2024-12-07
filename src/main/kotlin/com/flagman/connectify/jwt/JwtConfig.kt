package com.flagman.connectify.jwt

import io.jsonwebtoken.security.Keys
import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Getter
@Component
class JwtConfig(
    @Value("\${flagman.jwt.secret}")
    private val secret: String = "",
    @Value("\${flagman.jwt.access-token-expiration:60000}")
    private val accessTokenExpiration: Long = 60000,
    @Value("\${flagman.jwt.refresh-token-expiration:3600000}")
    private val refreshTokenExpiration: Long = 60000
) {
    private var jwtKey: SecretKey? = null

    fun secret() = secret
    fun getRefreshTokenExpiration() = refreshTokenExpiration
    fun getAccessTokenExpiration() = accessTokenExpiration
    fun getJwtKey() = jwtKey

    constructor(secret: String): this(secret = "", accessTokenExpiration = 0L, refreshTokenExpiration = 0L) {
        jwtKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))
    }
}