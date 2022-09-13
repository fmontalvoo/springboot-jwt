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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date createdAt;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@JsonManagedReference
	@JoinColumn(name = "factura_id")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItemFactura> items;

	private static final long serialVersionUID = 1L;

	/**
	 * Asigan fecha de creacion antes de persistir en la base de datos.
	 */
	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void addItemFactura(ItemFactura item) {
		if (this.items == null)
			this.items = new ArrayList<>();
		this.items.add(item);
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public Double getTotal() {
		Double total = 0.0;
		for (ItemFactura itemFactura : items) {
			total += itemFactura.calcularSubTotal();
		}
		String res = String.format("%.2f", total).replace(",", ".");
		return Double.parseDouble(res);
	}

}
