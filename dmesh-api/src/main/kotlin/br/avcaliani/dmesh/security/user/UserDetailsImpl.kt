package br.avcaliani.dmesh.security.user

import br.avcaliani.dmesh.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: User) : UserDetails {
    override fun getAuthorities() = mutableListOf<GrantedAuthority>()
    override fun isEnabled() = true
    override fun getUsername() = user.username
    override fun isCredentialsNonExpired() = true
    override fun getPassword() = user.password
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
}