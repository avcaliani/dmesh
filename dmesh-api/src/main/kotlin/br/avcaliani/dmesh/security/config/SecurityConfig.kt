package br.avcaliani.dmesh.security.config

import br.avcaliani.dmesh.security.token.JWTAuthenticationFilter
import br.avcaliani.dmesh.security.token.JWTAuthorizationFilter
import br.avcaliani.dmesh.security.token.JWTUtil
import br.avcaliani.dmesh.util.V1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @Autowired
    private lateinit var service: UserDetailsService

    @Bean
    fun pwdEncoder() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager(), jwtUtil))
            .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil, service))
            .sessionManagement()
            .sessionCreationPolicy(STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(service).passwordEncoder(pwdEncoder())
    }

}