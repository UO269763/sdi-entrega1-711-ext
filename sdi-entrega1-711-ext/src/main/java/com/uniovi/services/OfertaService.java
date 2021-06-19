package com.uniovi.services;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;
import com.uniovi.repositories.OfertasRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class OfertaService {

	@Autowired
	private OfertasRepository ofertasRepository; // repositorio de ofertas

	@Autowired
	private UsersRepository usersRepository; // repositorio de usuarios

	@Autowired
	private UsersService usersService; // servicio de usuarios

	/**
	 * Obtiene las ofertas de un usuario dado
	 * 
	 * @param pageable
	 * @param user
	 * @return Page<Oferta>
	 */
	public Page<Oferta> getOfertas(Pageable pageable, User user) {
		return ofertasRepository.findByUser(pageable, user);
	}

	/**
	 * Obtiene una oferta según id
	 * 
	 * @param id
	 * @return oferta
	 */
	public Oferta getOferta(Long id) {
		return ofertasRepository.findById(id).get();
	}

	/**
	 * Añade una oferta al repositorio y le asigna un usuario
	 * 
	 * @param oferta
	 * @param user
	 */
	public void addOferta(Oferta oferta, User user) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		oferta.setUser(user);
		ofertasRepository.save(oferta);
	}

	/**
	 * Edita una oferta
	 * 
	 * @param oferta
	 */
	public void editOferta(Oferta oferta) {
		ofertasRepository.save(oferta);
	}

	/**
	 * Borra una oferta
	 * 
	 * @param id
	 */
	public void deleteOferta(Long id) {
		ofertasRepository.deleteById(id);
	}

	/**
	 * Metodo auxiliar que se llama desde el controlador de ofertas que sirve para
	 * comprar una oferta a partir del id de la oferta y el usuario interesado en la
	 * oferta
	 * 
	 * @param id
	 * @param user
	 */
	public void comprarOfertaIdOf(Long id, User user) {
		comprarOferta(user, ofertasRepository.findById(id).get());
	}

	/**
	 * Metodo para comprar una oferta. Se le pasa como parámetros el usuario
	 * comprador y la oferta que desea comprar
	 * 
	 * @param usuarioComprador
	 * @param oferta
	 */
	public void comprarOferta(User usuarioComprador, Oferta oferta) {
		User usuarioVendedor = usersRepository.findByEmail(oferta.getUser().getEmail());
		// actualizamos el saldo del vendedor y del comprador
		usuarioComprador.setDinero(usuarioComprador.getDinero() - oferta.getPrecio());
		usuarioVendedor.setDinero(usuarioVendedor.getDinero() + oferta.precio);
		oferta.setComprador(usuarioComprador);
		usuarioComprador.comprarOferta(oferta);
		oferta.setComprable(false);
		usersService.editUser(usuarioComprador);
		editOferta(oferta);

	}

	/**
	 * Retorna el precio de una oferta según su id
	 * 
	 * @param id
	 * @return double
	 */
	public double precioOferta(Long id) {
		Optional<Oferta> oferta = ofertasRepository.findById(id);
		return oferta.get().getPrecio();
	}

	/**
	 * Busca una oferta según una cadena de texto pasada y exceptuando las del
	 * usuario pasado como parámetro. El usuario que se pasa por parámetro es del
	 * que no queremos que salgan las ofertas.
	 * 
	 * @param pageable
	 * @param searchText
	 * @param user
	 * @return ofertas
	 */
	public Page<Oferta> searchOfertaByTitulo(Pageable pageable, String searchText, User user) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		searchText = "%" + searchText + "%";

		ofertas = ofertasRepository.findByTitulo(pageable, searchText, user);

		return ofertas;
	}

	/**
	 * Busca todas las ofertas del repositorio
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Oferta> searchAllOfertas(Pageable pageable) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());

		ofertas = ofertasRepository.findAllOfertas(pageable);

		return ofertas;
	}

	/**
	 * Busca todas las ofertas, excepto las del usuario pasado como parámetro.
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<Oferta> searchAllOfertasUnlessMine(Pageable pageable, User user) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());

		ofertas = ofertasRepository.findAllOfertasUnlessMine(pageable, user);

		return ofertas;
	}

	/**
	 * Guarda en la base de datos la compra del usuario por la oferta dada.
	 * 
	 * @param user1
	 * @param oferta1
	 */
	public void guardarCompra(User user1, Oferta oferta1) {
		usersRepository.save(user1);
		ofertasRepository.save(oferta1);

	}

}
