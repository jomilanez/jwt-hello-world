package com.example.demo


import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

val SECRET = "SecretKeyToGenJWTs"
val EXPIRATION_TIME: Long = 864000000 // 10 days
val TOKEN_PREFIX = "Bearer "
val HEADER_STRING = "Authorization"
val SIGN_UP_URL = "/users/sign-up"


class JWTAuthenticationFilter(private val authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    override fun getAuthenticationManager() = authenticationManager

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest,
                                       res: HttpServletResponse): Authentication {
        try {
            val creds = ObjectMapper().readValue(req.inputStream, ApplicationUser::class.java)
            val authRequest = UsernamePasswordAuthenticationToken(creds.username,
                    creds.password, emptyList())
            setDetails(req, authRequest)
            return authenticationManager.authenticate(authRequest)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse,
                                          chain: FilterChain,
                                          auth: Authentication) {
        val token = Jwts.builder()
                .setSubject((auth.principal as User).username)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}