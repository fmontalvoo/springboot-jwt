package com.fmontalvoo.springboot.jwt.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;
	@NotBlank
	private String apellido;

	@Column(name = "foto_url")
	private String fotoUrl;

	@Email
	@NotBlank
	private String email;

	@JsonManagedReference
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date createdAt;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombreCompleto() {
		return this.nombre.concat(" ").concat(apellido);
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void addFactura(Factura factura) {
		if (this.facturas == null)
			this.facturas = new ArrayList<>();
		facturas.add(factura);
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fotoUrl=" + fotoUrl
				+ ", email=" + email + ", createdAt=" + createdAt + "]";
	}

	/**
	 * Asigan fecha de creacion antes de persistir en la base de datos.
	 */
	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	/**
	 * Asigan fecha de creacion antes de actualizar.
	 */
	@PreUpdate
	public void preUpdate() {
		this.createdAt = new Date();
	}

}
