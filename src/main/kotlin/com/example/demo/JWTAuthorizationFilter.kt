package com.example.demo

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.ArrayList
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import io.jsonwebtoken.Jwts
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



class JWTAuthorizationFilter(val authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest,
                                   res: HttpServletResponse,
                                   chain: FilterChain) {
        val header = req.getHeader(HEADER_STRING)
        if (header == null || !header!!.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            // parse the token.
            val user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token!!.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject
            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
            } else null
        }
        return null
    }

}