package br.avcaliani.dmesh.security.token

import br.avcaliani.dmesh.security.user.Credentials
import br.avcaliani.dmesh.security.user.UserDetailsImpl
import br.avcaliani.dmesh.util.AUTHORIZATION
import br.avcaliani.dmesh.util.BEARER
import br.avcaliani.dmesh.util.V1
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter : UsernamePasswordAuthenticationFilter {

    private var jwtUtil: JWTUtil

    constructor(
        authenticationManager: AuthenticationManager,
        jwtUtil: JWTUtil,
        loginURL: String = "$V1/auth"
    ) : super() {
        this.authenticationManager = authenticationManager
        this.jwtUtil = jwtUtil
        setFilterProcessesUrl(loginURL)
    }

    override fun attemptAuthentication(req: HttpServletRequest, response: HttpServletResponse?): Authentication? {
        try {
            val (username, password) = ObjectMapper().readValue(req.inputStream, Credentials::class.java)
            val token = UsernamePasswordAuthenticationToken(username, password)
            return authenticationManager.authenticate(token)
        } catch (ex: Exception) {
            throw UsernameNotFoundException("User not found!", ex)
        }
    }

    override fun successfulAuthentication(
        req: HttpServletRequest?,
        res: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val username = (authResult.principal as UserDetailsImpl).username
        val token = jwtUtil.generateToken(username)
        res.addHeader(AUTHORIZATION, "$BEARER $token")
    }

}
