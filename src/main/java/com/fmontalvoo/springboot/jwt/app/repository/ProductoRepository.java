package com.fmontalvoo.springboot.jwt.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fmontalvoo.springboot.jwt.app.entities.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> buscarPorNombre(String nombre);

	public List<Producto> findByNombreLikeIgnoreCase(String nombre);

}
