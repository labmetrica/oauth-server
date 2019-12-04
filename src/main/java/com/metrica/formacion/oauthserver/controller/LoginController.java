package com.metrica.formacion.oauthserver.controller;

import com.metrica.formacion.oauthserver.entity.UserCredentials;
import com.metrica.formacion.oauthserver.entity.usuarios;
import com.metrica.formacion.oauthserver.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity<usuarios> newUser (@RequestBody UserCredentials userCredentials) {

        if (usuariosService.exist(userCredentials.getId())) {

            usuarios usuario = usuariosService.buscar(userCredentials.getId());
            usuario.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
            usuario.setUsername(userCredentials.getUsername());

            if (userCredentials.getRoles() != null) {

                usuario.setRoles(userCredentials.getRoles());
            }

            return new ResponseEntity<>(usuariosService.nuevoUsuario(usuario), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
