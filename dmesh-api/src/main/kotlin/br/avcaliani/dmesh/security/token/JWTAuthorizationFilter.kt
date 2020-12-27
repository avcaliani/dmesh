package br.avcaliani.dmesh.security.token

import br.avcaliani.dmesh.util.AUTHORIZATION
import br.avcaliani.dmesh.util.BEARER
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter : BasicAuthenticationFilter {

    private var jwtUtil: JWTUtil
    private var service: UserDetailsService

    constructor(
        authenticationManager: AuthenticationManager,
        jwtUtil: JWTUtil,
        service: UserDetailsService
    ) : super(authenticationManager) {
        this.jwtUtil = jwtUtil
        this.service = service
    }

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader(AUTHORIZATION)
        if (header != null && header.startsWith(BEARER))
            SecurityContextHolder.getContext().authentication = getAuthentication(header)
        chain.doFilter(req, res)
    }

    private fun getAuthentication(header: String?): UsernamePasswordAuthenticationToken {
        val token = header?.substring(7) ?: ""
        if (jwtUtil.isValid(token)) {
            val username = jwtUtil.toClaims(token)?.subject
            val user = service.loadUserByUsername(username)
            return UsernamePasswordAuthenticationToken(user, null, user.authorities)
        }
        throw UsernameNotFoundException("Auth invalid!")
    }

}