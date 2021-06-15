package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private double dinero=100;
	private String role;

	private String password;

	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Oferta> ofertas;
	
	@OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
	private Set<Oferta> ofertasCompradas;
	
	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.dinero=100;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public void setDinero(double dinero) {
		this.dinero=dinero;
	}
	
	public double getDinero() {
		return dinero;
	}

	public void setRole(String role) {
		this.role=role;
	}

	public String getRole() {
		return role;
	}

	public Set<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(Set<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public Set<Oferta> getOfertasCompradas() {
		return ofertasCompradas;
	}

	public void setOfertasCompradas(Set<Oferta> ofertasCompradas) {
		this.ofertasCompradas = ofertasCompradas;
	}
	
	public void comprarOferta(Oferta oferta) {
		this.ofertasCompradas.add(oferta);
	}
	
	@Override
	public String toString() {
		return email;
	}

}
