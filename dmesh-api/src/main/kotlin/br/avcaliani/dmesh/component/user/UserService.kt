package br.avcaliani.dmesh.component.user

import br.avcaliani.dmesh.security.user.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun find(username: String?): User? = userRepository.findByUsername(username)

    fun myself(): User? = find(getCurrentUsername())

    private fun getCurrentUsername(): String =
        (SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl).username
}