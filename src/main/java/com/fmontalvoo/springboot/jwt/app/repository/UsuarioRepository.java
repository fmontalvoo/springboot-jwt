package com.fmontalvoo.springboot.jwt.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.fmontalvoo.springboot.jwt.app.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username); 
	
}
