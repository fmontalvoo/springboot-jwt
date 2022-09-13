package com.fmontalvoo.springboot.jwt.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fmontalvoo.springboot.jwt.app.entities.Cliente;
import com.fmontalvoo.springboot.jwt.app.entities.Factura;
import com.fmontalvoo.springboot.jwt.app.entities.Producto;

public interface IClienteService {
	public void save(Cliente cliente);

	public Cliente findById(Long id);

	public Cliente fetchByIdWithFacturas(Long id);

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public void delete(Long id);

	public List<Producto> findProductosByName(String name);

	public Producto findProductoById(Long id);

	public void saveFactura(Factura factura);

	public Factura findFacturaById(Long id);

	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

	public void deleteFactura(Long id);
}
