package com.metrica.formacion.oauthserver.service;

import com.metrica.formacion.oauthserver.dao.usuariosRepository;
import com.metrica.formacion.oauthserver.entity.usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServiceImple implements UsuariosService{

    @Autowired
    private usuariosRepository usuariosRepository;

    @Override
    public usuarios buscar(Integer id) {
        if(usuariosRepository.existsById(id)){

            return usuariosRepository.findById(id).get();
        }

        return null;
    }

    @Override
    public boolean exist (Integer id){
        return usuariosRepository.existsById(id);
    }

    @Override
    public usuarios nuevoUsuario(usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    @Override
    public usuarios nuevoRol(usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    @Override
    public usuarios revocarpermisos(usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }
}
