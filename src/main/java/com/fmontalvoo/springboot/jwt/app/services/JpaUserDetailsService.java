package com.fmontalvoo.springboot.jwt.app.services;

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

import com.fmontalvoo.springboot.jwt.app.entities.Rol;
import com.fmontalvoo.springboot.jwt.app.entities.Usuario;
import com.fmontalvoo.springboot.jwt.app.repository.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);

		if (usuario == null) {
			logger.error("No se encontro al usuario: ".concat(username));
			throw new UsernameNotFoundException("No se encontro al usuario: ".concat(username));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (Rol rol : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}

		if (authorities.isEmpty())
			throw new UsernameNotFoundException(
					"El usuario: ".concat(username).concat(" no posee ningun rol de acceso."));

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

}
