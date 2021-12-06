package com.bruce.proyecto.ProyectoCitas.models.service;

import com.bruce.proyecto.ProyectoCitas.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsuario(String username);
	
}
