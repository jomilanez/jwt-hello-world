package com.example.demo

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl() : UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): User {
        val user = ApplicationUser()
        return User(user.username, user.password, emptyList())
    }
}