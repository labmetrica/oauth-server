package com.metrica.formacion.oauthserver.security;

import com.metrica.formacion.oauthserver.JWT.JwtRSAkey;
import com.metrica.formacion.oauthserver.entity.UserCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/**","POST"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {

            UserCredentials userCredentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword(), Collections.emptyList());

            return authenticationManager.authenticate(token);
        }

        catch (IOException e){

            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600 * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, JwtRSAkey.RSA_PUBLICA.getBytes())
                .compact();

        response.addHeader("Authorization", token);
    }
}
