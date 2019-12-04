package com.metrica.formacion.oauthserver.service;

import com.metrica.formacion.oauthserver.entity.usuarios;

public interface UsuariosService {

    usuarios buscar(Integer id);

    usuarios nuevoUsuario (usuarios usuarios);

    usuarios nuevoRol (usuarios usuarios);

    usuarios revocarpermisos (usuarios usuarios);

    boolean exist (Integer id);
}
