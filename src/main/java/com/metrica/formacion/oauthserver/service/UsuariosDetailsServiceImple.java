package com.metrica.formacion.oauthserver.service;

import com.metrica.formacion.oauthserver.dao.usuariosRepository;
import com.metrica.formacion.oauthserver.entity.usuarios;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UsuariosDetailsServiceImple implements UserDetailsService {

    @Autowired
    private com.metrica.formacion.oauthserver.dao.usuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        usuarios usuario = usuariosRepository.findByUsername(username);

        if(usuario == null){
            log.error("el usuario: " + username + ", no existe");
            throw new UsernameNotFoundException("el usuario: " + username + ", no existe");
        }

        List<GrantedAuthority> grantedAuthorities = usuario.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getNombre()))
                .peek(authority -> log.info("role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername(),usuario.getPassword(),usuario.isActivo(),
                true,true,true,grantedAuthorities);
    }
}