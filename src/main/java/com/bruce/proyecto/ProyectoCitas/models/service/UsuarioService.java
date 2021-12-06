package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IUsuarioDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Usuario;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsuario(username);
		if (usuario == null) {
			logger.error("No existe usuario en el sistema");
			throw new UsernameNotFoundException("No existe usuario en el sistema");
		}
		List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();
		autoridades.add(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
		return new User(usuario.getUsuario(), usuario.getPass(), usuario.getEnabled(), true, true, true, autoridades);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsuario(String username) {
		return usuarioDao.findByUsuario(username);
	}

}
