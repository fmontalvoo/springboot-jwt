package com.fmontalvoo.springboot.jwt.app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "factura_items")
public class ItemFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	private Factura factura;

//	@JoinColumn(name = "producto_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer" })
	private Producto producto;

	private static final long serialVersionUID = 1L;

	public ItemFactura() {
	}

	public ItemFactura(Integer cantidad, Producto producto) {
		this.cantidad = cantidad;
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@XmlTransient
	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Double calcularSubTotal() {
		String res = String.format("%.2f", this.cantidad.doubleValue() * this.producto.getPrecio()).replace(",", ".");
		return Double.parseDouble(res);
	}
}
