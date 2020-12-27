package br.avcaliani.dmesh.security.user

import br.avcaliani.dmesh.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var service: UserService

    override fun loadUserByUsername(username: String?): UserDetails =
        UserDetailsImpl(service.find(username) ?: throw UsernameNotFoundException(username))
}