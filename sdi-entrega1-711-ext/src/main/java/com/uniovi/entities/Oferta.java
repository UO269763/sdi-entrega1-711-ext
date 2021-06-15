package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "oferta")
public class Oferta {
	
	@Id
	@GeneratedValue
	public Long id;
	public String titulo;
	public String descripcion;
	public Date fecha = new Date();
	public double precio;
	
	private Boolean comprable=true;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "comprador")
	private User comprador;
	
	public Oferta(String titulo, String descripcion, int precio) {
		super();
		this.titulo=titulo;
		this.descripcion=descripcion;
		this.precio=precio;
	}
	
	public Oferta(User user, String titulo, String descripcion, int precio) {
		this(titulo,descripcion,precio);
		this.user=user;
	}
	
	public Oferta() {
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isComprable() {
		return comprable;
	}

	public void setComprable(boolean comprable) {
		this.comprable = comprable;
	}

	public User getComprador() {
		return comprador;
	}

	public void setComprador(User comprador) {
		this.comprador = comprador;
	}
	 
	
}
