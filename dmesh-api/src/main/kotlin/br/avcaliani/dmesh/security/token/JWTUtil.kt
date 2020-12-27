package br.avcaliani.dmesh.security.token

import br.avcaliani.dmesh.handler.ErrorHandler
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {

    private val DEFAULT_EXPIRATION = 600L // 10m
    private val log: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration.secs}")
    private lateinit var expiration: String

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(toMillis()))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun isValid(token: String): Boolean {

        log.info("JWT Token: $token")
        val claims = toClaims(token) ?: return false

        val username = claims.subject ?: return false
        log.info("Username: $username")

        val expiration = claims.expiration ?: return false
        log.info("Expiration Date: $expiration")

        val now = Date(System.currentTimeMillis())
        log.info("Now: $now")

        return now.before(expiration)
    }

    fun toClaims(token: String): Claims? {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        } catch (ex: Exception) {
            log.warn("Error while parsing token.", ex)
            null
        }
    }

    private fun toMillis(): Long =
        System.currentTimeMillis() + (1000L * (expiration.toLongOrNull() ?: DEFAULT_EXPIRATION))
}