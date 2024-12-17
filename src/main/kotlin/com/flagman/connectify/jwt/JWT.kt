package com.flagman.connectify.jwt

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWT(
    private val jwtConfig: JwtConfig
) {
    fun generate(username: String): String {
        val now = Date()
        val expiration = Date(now.time + jwtConfig.getAccessTokenExpiration())

        return Jwts.builder()
            .setSubject(username)
            .signWith(jwtConfig.getJwtKey())
            .setIssuedAt(now)
            .setExpiration(expiration)
            .compact()
    }

    fun validate(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getJwtKey())
                .build()
                .parseClaimsJws(token)
            true
        } catch (_:  io. jsonwebtoken.ExpiredJwtException) {
            false
        }
    }

    fun getUsernameFromToken(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(jwtConfig.getJwtKey())
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject
    }
}