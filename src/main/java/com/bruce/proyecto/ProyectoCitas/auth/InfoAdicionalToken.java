package com.bruce.proyecto.ProyectoCitas.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.bruce.proyecto.ProyectoCitas.models.entity.Usuario;
import com.bruce.proyecto.ProyectoCitas.models.service.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		Usuario usuario = usuarioService.findByUsuario(authentication.getName());
		Map<String, Object> infor = new HashMap<>();
		infor.put("nombre", usuario.getNombre());
		infor.put("apellido", usuario.getApellido());
		infor.put("id", usuario.getId()); 
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infor);
		return accessToken;
	}

}
