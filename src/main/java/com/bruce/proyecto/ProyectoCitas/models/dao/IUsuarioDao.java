package com.bruce.proyecto.ProyectoCitas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruce.proyecto.ProyectoCitas.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

	public Usuario findByUsuario(String usuario);

}
